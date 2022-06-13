package wwf.org.tenant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="tn_emails_configuration")
@Data
public class EmailConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name="tenantId", unique = true, referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Tenant tenantId;

    @NotEmpty(message = "email_host_vacio")
    @Column(nullable = false)
    private String emailHost;

    @NotEmpty(message = "email_username_vacio")
    @Column(nullable = false)
    private String emailUsername;

    @NotEmpty(message = "email_password_vacio")
    @Column(nullable = false)
    private String emailPassword;

    @NotEmpty(message = "email_port_vacio")
    @Column(nullable = false)
    private String emailPort;

    @NotEmpty(message = "email_from_vacio")
    @Column(nullable = false)
    private String emailFrom;

    @NotEmpty(message = "email_from_name_vacio")
    @Column(nullable = false)
    private String emailFromName;

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
