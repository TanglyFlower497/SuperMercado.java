package br.edu.ifsuldeminas.supermercado.entidade;

import java.io.Serializable;

/**
 * Usuário do sistema (tabela "usuario").
 * Implementa Serializable porque o objeto é guardado na sessão HTTP.
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nomeUsuario;
    private String senhaHash;   // hash BCrypt (60 caracteres), nunca a senha original
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
