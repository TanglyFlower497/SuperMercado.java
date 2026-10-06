package br.edu.ifsuldeminas.supermercado.servico;

/**
 * Lê configurações sensíveis (SMTP, URL pública) de variáveis de ambiente
 * ou de propriedades da JVM (-DNOME=valor), para que senhas NUNCA fiquem no código.
 */
public final class AppConfig {

    private AppConfig() {
    }

    public static String get(String chave, String padrao) {
        String valor = System.getenv(chave);
        if (valor == null || valor.isBlank()) {
            valor = System.getProperty(chave);
        }
        return (valor == null || valor.isBlank()) ? padrao : valor.trim();
    }
}
