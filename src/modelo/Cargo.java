package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Cargo extends Entidade  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "A descrição do cargo não pode está em branco!")
    private String descricao;

    @Min(value = 0, message = "o valor mínimo de carga horária mensal para o cargo é zero (0)!")
    @Max(value = 240, message = "o valor máximo de carga horária mensal para o cargo é 240 horas!")
    private int cargaHorariaMensal;

    public Cargo(Integer id, String descricao, int cargaHorariaMensal) {
        this.id = id;
        this.descricao = descricao;
        this.cargaHorariaMensal = cargaHorariaMensal;
    }

    public Cargo(){

    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCargaHorariaMensal() {
        return cargaHorariaMensal;
    }

    public void setCargaHorariaMensal(int cargaHorariaMensal) {
        this.cargaHorariaMensal = cargaHorariaMensal;
    }

    @Override
    public String toString() {
        return "Cargo{" + "id=" + id + ", descricao=" + descricao + ", cargaHorariaMensal=" + cargaHorariaMensal + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.descricao);
        hash = 23 * hash + Objects.hashCode(this.cargaHorariaMensal);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cargo other = (Cargo) obj;
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.cargaHorariaMensal, other.cargaHorariaMensal);
    }
}
