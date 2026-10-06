# Sessão de usuário no SuperMercado — guia de configuração e explicação

## 1. Como colocar para rodar

1. **Banco:** importe o `mercado.sql` e, **depois**, execute `sql/usuario.sql`
   (ele cria `usuario` e `token_recuperacao`). Atenção: o `mercado.sql` começa com
   `DROP DATABASE`, então reimportá-lo apaga os usuários — rode o `usuario.sql` de novo.
2. **Build:** `mvn clean package` (baixa o `jbcrypt` e o `angus-mail` adicionados ao `pom.xml`).
3. **E-mail (recuperação de senha):** defina as variáveis de ambiente do servidor
   (GlassFish/Payara) **antes de iniciá-lo**:

   | Variável | Exemplo | Observação |
   |---|---|---|
   | `MAIL_USER` | `seuemail@gmail.com` | conta SMTP |
   | `MAIL_PASS` | `abcd efgh ijkl mnop` | no Gmail: *Senha de app* (exige verificação em 2 etapas) |
   | `APP_BASE_URL` | `http://localhost:8080/Supermercado-1.0-SNAPSHOT` | usado no link do e-mail |
   | `MAIL_HOST` / `MAIL_PORT` / `MAIL_FROM` | opcionais | padrão: `smtp.gmail.com` / `587` / `MAIL_USER` |

   Sem `MAIL_USER`/`MAIL_PASS`, o e-mail **não é enviado** e o link aparece no log do servidor
   (modo de desenvolvimento). Para a apresentação, configure o SMTP de verdade.
4. Acesse `http://localhost:8080/Supermercado-1.0-SNAPSHOT/` → você será levado ao login.

## 2. Roteiro de teste (o que a entrega pede)

1. `CadastrarUsuario.jsp` → cadastre um usuário → no banco, `senhahash` começa com `$2a$12$` (60 caracteres).
2. Tente abrir `Home.jsp` (ou qualquer CRUD) **sem** login → redireciona para `login.jsp`.
3. Faça login → entra na `Home.jsp`; navegue pelos cadastros.
4. Clique em **Logout** → volta ao login; ao apertar "voltar" no navegador, a página protegida não reaparece.
5. **Esqueci minha senha** → informe o e-mail → abra o link recebido → defina a nova senha → entre com ela.
6. Abra o **mesmo link de novo** → "Link inválido, expirado ou já utilizado" (token invalidado).

## 3. Perguntas da apresentação

**Como a senha é armazenada?** Nunca em texto puro. No cadastro, `SegurancaUtil.gerarHashSenha`
chama `BCrypt.hashpw(senha, BCrypt.gensalt(12))`. O resultado (`$2a$12$<22 chars de salt><31 chars de hash>`,
60 no total) é o que vai para `usuario.senhahash`. O *salt* é aleatório e fica dentro do próprio hash,
então duas pessoas com a mesma senha têm hashes diferentes.

**Como o BCrypt verifica a senha?** Não há como "desfazer" o hash. `BCrypt.checkpw(digitada, hashDoBanco)`
lê o custo e o salt gravados no hash, calcula o BCrypt da senha digitada com esses mesmos parâmetros e
compara com o hash salvo. Iguais → senha correta. O custo 12 deixa cada tentativa deliberadamente lenta
(~0,2 s), o que encarece ataques de força bruta.

**Como a sessão é criada?** No `LoginControlador.doPost`, depois de `checkpw` retornar `true`: a sessão
antiga é descartada (evita *session fixation*), `request.getSession(true)` cria uma nova e o objeto `Usuario`
(sem o hash) é gravado em `sessao.setAttribute("usuarioLogado", usuario)`. O servidor envia ao navegador o
cookie `JSESSIONID`, que identifica essa sessão nas próximas requisições. Expira após 30 min de inatividade
(`web.xml`).

**Como o AuthFilter identifica se está autenticado?** Ele é mapeado em `/*`, então roda antes de qualquer
Servlet/JSP. Se o caminho está na lista pública (login, cadastro, recuperação, `/css/`), deixa passar. Senão,
faz `request.getSession(false)` (sem criar sessão) e verifica se existe o atributo `usuarioLogado`. Tem →
`chain.doFilter` (segue para a página). Não tem → `sendRedirect` para `login.jsp`.

**Como o logout encerra a sessão?** O link do menu faz GET em `LoginControlador`, que chama
`sessao.invalidate()`: o servidor apaga a sessão e seus atributos. O cookie antigo passa a não valer nada,
então o `AuthFilter` não encontra `usuarioLogado` e bloqueia o acesso.

**Como funciona a recuperação de senha?**
1. `EsqueciSenha.jsp` envia o e-mail ao `RecuperarSenhaControlador` (`opcao=solicitar`).
2. Busca o usuário pelo e-mail. Se existir, gera um token aleatório de 256 bits (`SecureRandom`).
3. No banco grava só o **SHA-256** do token + validade de 30 min (`token_recuperacao`) e invalida tokens
   anteriores pendentes do mesmo usuário.
4. Envia por e-mail o link `.../RecuperarSenhaControlador?opcao=redefinir&token=<token>`.
5. Ao abrir o link, o sistema calcula o SHA-256 do token recebido e confere: existe, `usado = 0` e não expirou?
   Se sim, mostra `RedefinirSenha.jsp`.
6. Ao salvar, gera o novo hash BCrypt e, **em uma única transação** (`TokenRecuperacaoDao.redefinirSenha`),
   atualiza `usuario.senhahash` e marca o token como `usado = 1`. O `SELECT ... FOR UPDATE` trava a linha, então
   dois cliques simultâneos no mesmo link não conseguem reutilizá-lo.

## 4. Decisões de segurança (por quê)

- **Token com hash no banco:** quem lesse a tabela não conseguiria montar um link válido.
- **Mensagem genérica no "Esqueci minha senha"** e no login: não revela se um e-mail/usuário existe.
- **Senha só via POST** (nunca na URL); saídas das JSPs com `<c:out>` (evita XSS).
- **`APP_BASE_URL`:** o link do e-mail não depende do cabeçalho `Host`, que pode ser forjado.
- **Senha de 6 a 72 caracteres:** o BCrypt ignora tudo após 72 bytes; a regra evita truncamento silencioso.
  Para endurecer, aumente `SENHA_MIN` em `SegurancaUtil`.
- O cadastro de usuários é **público** (a atividade exige cadastro antes do login). Em produção, restrinja.

## 5. Arquivos novos/alterados

Novos: `entidade/Usuario`, `modelo/dao/UsuarioDao`, `modelo/dao/TokenRecuperacaoDao`, `servico/AuthFilter`,
`servico/SegurancaUtil`, `servico/EmailServico`, `servico/AppConfig`, `controlador/LoginControlador`,
`controlador/UsuarioControlador`, `controlador/RecuperarSenhaControlador`, `login.jsp`, `CadastrarUsuario.jsp`,
`EsqueciSenha.jsp`, `RedefinirSenha.jsp`, `sql/usuario.sql`.
Alterados: `pom.xml` (jbcrypt + angus-mail), `menu.jsp` e `Home.jsp` (Login/Logout), `index.html`,
`WEB-INF/web.xml` (cookie HttpOnly), `css/estilo.css` + `head.jsp` (estilos das telas, `?v=4`).
