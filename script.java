package com.exemplo.meuprojeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.*;

// ====================
// Classe principal
// ====================
@SpringBootApplication
public class MeuprojetoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeuprojetoApplication.class, args);
    }
}

// ====================
// Controller inicial para teste
// ====================
@RestController
class UsuarioController {

    @GetMapping("/test")
    public String teste() {
        return "Spring Boot funcionando!";
    }
}

// ====================
// Entidades básicas
// ====================
@Entity
@Table(name = "PESSOA")
class Pessoa {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    private String tel;

    @Column(nullable = false)
    private String senha;

    // getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}

@Entity
@Table(name = "PRODUTO")
class Produto {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "DATA_INC", nullable = false)
    private java.sql.Date dataInc;

    @Column(name = "DATA_FIN", nullable = false)
    private java.sql.Date dataFin;

    private String status;

    // getters e setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public java.sql.Date getDataInc() { return dataInc; }
    public void setDataInc(java.sql.Date dataInc) { this.dataInc = dataInc; }

    public java.sql.Date getDataFin() { return dataFin; }
    public void setDataFin(java.sql.Date dataFin) { this.dataFin = dataFin; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

@Entity
@Table(name = "PESSOA_PRODUTO")
class PessoaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}