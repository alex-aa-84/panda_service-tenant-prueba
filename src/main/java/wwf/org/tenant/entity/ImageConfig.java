package wwf.org.tenant.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="tn_images_config")
@Data
public class ImageConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Lob
    private byte[] image;

    private String type;
    private String name;

    private Long create_by;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    private Long last_update_by;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_update_date;

    @Column(unique = true, nullable = false)
    @Size(min=32, max = 32)
    private String ctrlMd5;

}
