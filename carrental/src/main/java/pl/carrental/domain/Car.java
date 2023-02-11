package pl.carrental.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "mark", nullable = false, length = 20)
    private String mark;
    @Column(name = "model", nullable = false, length = 20)
    private String model;
    @Column(name = "per_day", nullable = false)
    private BigDecimal perDay;

    @ManyToOne
    private Department department;
    @Transient
    private Long departmentId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPerDay() {
        return perDay;
    }

    public void setPerDay(BigDecimal perDay) {
        this.perDay = perDay;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(getId(), car.getId()) && Objects.equals(getMark(), car.getMark()) && Objects.equals(getModel(), car.getModel()) && Objects.equals(getPerDay(), car.getPerDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMark(), getModel(), getPerDay());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", perDay=" + perDay +
                ", department=" + department +
                ", departmentId=" + departmentId +
                '}';
    }
}
