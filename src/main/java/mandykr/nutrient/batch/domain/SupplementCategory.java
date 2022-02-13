package mandykr.nutrient.batch.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupplementCategory {
    public static final int MIN_DEPTH = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType type;
    private String name;
    private int depth;

    public SupplementCategory(CategoryType type, String name, int depth) {
        this.type = type;
        this.name = name;
        this.depth = depth;
    }
}
