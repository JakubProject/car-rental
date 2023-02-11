package pl.carrental.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false, length = 30)
    private String name;
    @Column(name = "description", length = 255)
    private String description;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getRoles().remove(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        return Objects.equals(description, other.description) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
}
