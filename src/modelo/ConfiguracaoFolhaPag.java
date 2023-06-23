package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Range;

@Entity
public class ConfiguracaoFolhaPag extends Entidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "O ano de vigência não pode ser nulo!")
    @Range(min = 1900, max = 2030, message = "O ano de vigência deve estar contido no intervalo entre 1900 a 2030")
    private Integer anoVigencia;
    private boolean ativo;
    @NotNull(message = "O valor dia do vale alimentação não pode ser nulo!")
    @PositiveOrZero(message="O valor dia do vale alimentação não pode ser menor que zero")
    private Double valorDiaValeAlimentacao;
    @NotNull(message = "O valor dia do vale transporte não pode ser nulo!")
    @PositiveOrZero(message="O valor dia do vale transporte não pode ser menor que zero")
    private Double valorDiaValeTransporte;
    @DecimalMax(value = "1", message = "O valor percentual de desconto do vale transporte não pode ser maior que 1 (um)!")
    @PositiveOrZero(message="percentualDescontoValeTransporte Não pode ser menor que zero")
    @NotNull(message = "O valor percentual de desconto do vale transporte não pode ser nulo!")
    private Double percentualDescontoValeTransporte;
    @DecimalMax(value = "1", message = "O valor percentual de desconto do vale alimentação não pode ser maior que 1 (um)!")
    @PositiveOrZero(message="percentualDescontoValeAlimentacao Não pode ser menor que zero")
    @NotNull(message = "O valor percentual de desconto do vale alimentação não pode ser nulo!")
    private Double percentualDescontoValeAlimentacao;

    public ConfiguracaoFolhaPag(){
        this.ativo = true;
    }

    public ConfiguracaoFolhaPag(Integer id, Integer anoVigencia, boolean ativo, Double valorDiaValeAlimentacao, Double valorDiaValeTransporte, Double percentualDescontoValeTransporte, Double percentualDescontoValeAlimentacao) {
        this.id = id;
        this.anoVigencia = anoVigencia;
        this.ativo = ativo;
        this.valorDiaValeAlimentacao = valorDiaValeAlimentacao;
        this.valorDiaValeTransporte = valorDiaValeTransporte;
        this.percentualDescontoValeTransporte = percentualDescontoValeTransporte;
        this.percentualDescontoValeAlimentacao = percentualDescontoValeAlimentacao;
    }


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnoVigencia() {
        return anoVigencia;
    }

    public void setAnoVigencia(Integer anoVigencia) {
        this.anoVigencia = anoVigencia;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Double getValorDiaValeAlimentacao() {
        return valorDiaValeAlimentacao;
    }

    public void setValorDiaValeAlimentacao(Double valorDiaValeAlimentacao) {
        this.valorDiaValeAlimentacao = valorDiaValeAlimentacao;
    }

    public Double getValorDiaValeTransporte() {
        return valorDiaValeTransporte;
    }

    public void setValorDiaValeTransporte(double valorDiaValeTransporte) {
        this.valorDiaValeTransporte = valorDiaValeTransporte;
    }

    public Double getPercentualDescontoValeTransporte() {
        return percentualDescontoValeTransporte;
    }

    public void setPercentualDescontoValeTransporte(Double percentualDescontoValeTransporte) {
        this.percentualDescontoValeTransporte = percentualDescontoValeTransporte;
    }

    public Double getPercentualDescontoValeAlimentacao() {
        return percentualDescontoValeAlimentacao;
    }

    public void setPercentualDescontoValeAlimentacao(Double percentualDescontoValeAlimentacao) {
        this.percentualDescontoValeAlimentacao = percentualDescontoValeAlimentacao;
    }

    @Override
    public String toString() {
        return "ConfiguracaoFolhaPag{" + "id=" + id + ", anoVigencia=" + anoVigencia + ", ativo=" + ativo + ", valorDiaValeAlimentacao=" + valorDiaValeAlimentacao + ", valorDiaValeTransporte=" + valorDiaValeTransporte + ", percentualDescontoValeTransporte=" + percentualDescontoValeTransporte + ", percentualDescontoValeAlimentacao=" + percentualDescontoValeAlimentacao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.anoVigencia);
        hash = 37 * hash + (this.ativo ? 1 : 0);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.valorDiaValeAlimentacao) ^ (Double.doubleToLongBits(this.valorDiaValeAlimentacao) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.valorDiaValeTransporte) ^ (Double.doubleToLongBits(this.valorDiaValeTransporte) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.percentualDescontoValeTransporte) ^ (Double.doubleToLongBits(this.percentualDescontoValeTransporte) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.percentualDescontoValeAlimentacao) ^ (Double.doubleToLongBits(this.percentualDescontoValeAlimentacao) >>> 32));
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
        final ConfiguracaoFolhaPag other = (ConfiguracaoFolhaPag) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorDiaValeAlimentacao) != Double.doubleToLongBits(other.valorDiaValeAlimentacao)) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorDiaValeTransporte) != Double.doubleToLongBits(other.valorDiaValeTransporte)) {
            return false;
        }
        if (Double.doubleToLongBits(this.percentualDescontoValeTransporte) != Double.doubleToLongBits(other.percentualDescontoValeTransporte)) {
            return false;
        }
        if (Double.doubleToLongBits(this.percentualDescontoValeAlimentacao) != Double.doubleToLongBits(other.percentualDescontoValeAlimentacao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.anoVigencia, other.anoVigencia);
    }
}
