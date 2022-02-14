package mandykr.nutrient.batch.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupplementCategoryBase {
    public static final int MIN_DEPTH = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_BASE_ID")
    private Long id;

    private String name;
    private int depth;

    public SupplementCategoryBase(String name, int depth) {
        this.name = name;
        this.depth = depth;
    }
}
