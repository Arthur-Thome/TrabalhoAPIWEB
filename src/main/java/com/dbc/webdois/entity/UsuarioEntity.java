package com.dbc.webdois.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity(name = "USUARIO")
public class UsuarioEntity implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "email")
    private String email;
    @Column(name = "login")
    private String login;
    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "USUARIO_GRUPO",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_grupo")
    )
    private List<GrupoEntity> grupos;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioEntity", fetch = FetchType.LAZY)
    private List<ReservaEntity> reservas;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (GrupoEntity grupoEntity : grupos) {
            grantedAuthorities.addAll(grupoEntity.getRegras());
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
