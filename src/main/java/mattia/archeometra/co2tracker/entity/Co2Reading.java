package mattia.archeometra.co2tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "CO2READING")
public class Co2Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Double co2Level;
    @Column
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

}
