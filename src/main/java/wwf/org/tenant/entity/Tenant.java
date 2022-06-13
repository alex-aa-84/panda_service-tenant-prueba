package wwf.org.tenant.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="tn_tenants")
@Data
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "inquilino_vacio")
    @Column(unique = true, nullable = false)
    private String tenant;

    @NotEmpty(message = "cliente_vacio")
    @Column(unique = true, nullable = false)
    private String clientId;

    @NotEmpty(message = "dominio_vacio")
    @Column(unique = true, nullable =  false)
    private String domain;

    @NotEmpty(message = "organizacion_vacio")
    @Column(unique = true, nullable =  false)
    private String organization;

    @NotEmpty(message = "departamento_wwf_vacio")
    @Column(unique = true, nullable = false)
    private String departmentWwf;

    @NotNull(message = "unidad_administrativa_nula")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private AdministrativeUnit administrativeUnit;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Country country;

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
