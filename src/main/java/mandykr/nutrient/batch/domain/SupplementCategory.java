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
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;
    private int depth;

    public SupplementCategory(String name, int depth) {
        this.name = name;
        this.depth = depth;
    }

    public static SupplementCategory of(SupplementCategoryBase base) {
        return new SupplementCategory(base.getName(), base.getDepth());
    }
}
