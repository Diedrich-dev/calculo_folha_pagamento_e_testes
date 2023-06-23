package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import util.validacoes.CTPS;


@Entity
@Table(name = "funcionario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Funcionario extends Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min=11, max=14)
    @Pattern(regexp = "\\d{3}\\.?\\d{5}\\.?\\d{2}-?\\d{1}", message = "Formato da ctps inválido")
    @CTPS
    private String ctps;

    @NotNull(message = "a data de admissao não ser nula")
    @PastOrPresent(message = "A data de admissão não pode ser maior que a data atual!")
    private LocalDate dataAdmissao;

    @PastOrPresent(message = "A data de demissão não pode ser maior que a data atual!")
    private LocalDate dataDemissao;

    @NotNull(message= "Cargo não pode ser nulo")
    @ManyToOne(optional = false)
    private Cargo cargo;

    @NotNull(message= "Salário nao pode ser nulo")
    @Positive(message= "O Salário deve ser maior que zero")
    private Double salario;

    private boolean recebeValeTransporte;
    private boolean recebeValeAlimentacao;

    @PositiveOrZero(message= "Número de dependentes nao pode ser menor que zero")
    private int numeroDependentes;

    public Funcionario(Integer id, String ctps, LocalDate dataAdmissao, LocalDate dataDemissao, Cargo cargo, Double salario, boolean recebeValeTransporte, boolean recebeValeAlimentacao, int numeroDependentes) {
        this.id = id;
        this.ctps = ctps;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
        this.cargo = cargo;
        this.salario = salario;
        this.recebeValeTransporte = recebeValeTransporte;
        this.recebeValeAlimentacao = recebeValeAlimentacao;
        this.numeroDependentes = numeroDependentes;
    }

    public Funcionario(Integer id, String nome, String cpf, String email, String telefone, Endereco endereco, String ctps, LocalDate dataAdmissao, LocalDate dataDemissao, Cargo cargo, Double salario, boolean recebeValeTransporte, boolean recebeValeAlimentacao, int numeroDependentes) {
        super(id, nome, cpf, email, telefone, endereco);
        this.id = id;
        this.ctps = ctps;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
        this.cargo = cargo;
        this.salario = salario;
        this.recebeValeTransporte = recebeValeTransporte;
        this.recebeValeAlimentacao = recebeValeAlimentacao;
        this.numeroDependentes = numeroDependentes;
    }

    public Funcionario() {
    }


    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public boolean isRecebeValeTransporte() {
        return recebeValeTransporte;
    }

    public void setRecebeValeTransporte(boolean recebeValeTransporte) {
        this.recebeValeTransporte = recebeValeTransporte;
    }

    public boolean isRecebeValeAlimentacao() {
        return recebeValeAlimentacao;
    }

    public void setRecebeValeAlimentacao(boolean recebeValeAlimentacao) {
        this.recebeValeAlimentacao = recebeValeAlimentacao;
    }

    public int getNumeroDependentes() {
        return numeroDependentes;
    }

    public void setNumeroDependentes(int numeroDependentes) {
        this.numeroDependentes = numeroDependentes;
    }

    @AssertTrue(message = "A data de demissão não pode ser anterior à data de admissão.")
    public boolean validarDatasDeAdmissaoDemissao(){
        return this.dataDemissao==null || this.dataDemissao.isAfter(dataAdmissao) ;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "id=" + id + ", ctps=" + ctps + ", dataAdmissao=" + dataAdmissao + ", dataDemissao=" + dataDemissao + ", cargo=" + cargo + ", salario=" + salario + ", recebeValeTransporte=" + recebeValeTransporte + ", recebeValeAlimentacao=" + recebeValeAlimentacao + ", numeroDependentes=" + numeroDependentes + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.ctps);
        hash = 97 * hash + Objects.hashCode(this.dataAdmissao);
        hash = 97 * hash + Objects.hashCode(this.dataDemissao);
        hash = 97 * hash + Objects.hashCode(this.cargo);
        hash = 97 * hash + Objects.hashCode(this.salario);
        hash = 97 * hash + (this.recebeValeTransporte ? 1 : 0);
        hash = 97 * hash + (this.recebeValeAlimentacao ? 1 : 0);
        hash = 97 * hash + this.numeroDependentes;
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
        final Funcionario other = (Funcionario) obj;
        if (this.recebeValeTransporte != other.recebeValeTransporte) {
            return false;
        }
        if (this.recebeValeAlimentacao != other.recebeValeAlimentacao) {
            return false;
        }
        if (this.numeroDependentes != other.numeroDependentes) {
            return false;
        }
        if (!Objects.equals(this.ctps, other.ctps)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataAdmissao, other.dataAdmissao)) {
            return false;
        }
        if (!Objects.equals(this.dataDemissao, other.dataDemissao)) {
            return false;
        }
        if (!Objects.equals(this.cargo, other.cargo)) {
            return false;
        }
        return Objects.equals(this.salario, other.salario);
    }
}
