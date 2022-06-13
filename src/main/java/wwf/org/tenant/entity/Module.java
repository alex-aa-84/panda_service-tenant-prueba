package wwf.org.tenant.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="tn_modules")
@Data
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "modulo_vacio")
    @Column(unique = true, nullable = false)
    private String module;

    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private ImageConfig imageConfig;

    @NotEmpty(message = "router_link_vacio")
    @Column(nullable = false)
    private String routerLink;

    @NotEmpty(message = "servicio_vacio")
    @Column(nullable = false)
    private String serviceUrl;

    @NotEmpty(message = "exposicion_vacio")
    @Column(nullable = false)
    private String exposeApi;

    private String serviceLanguage;
    private String serviceLanguageVersion;

    private String serviceGit;

    private Integer attribute1;
    private Integer attribute2;
    private Integer attribute3;
    private Integer attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;

    @Temporal(TemporalType.DATE)
    private Date attribute9 ;

    @Temporal(TemporalType.TIMESTAMP)
    private Date attribute10;

    private String status;
    private Long create_by;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    private Long last_update_by;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_update_date;
}
