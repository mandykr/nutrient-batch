package mandykr.nutrient.batch.openapi.dto.foodsafety;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import mandykr.nutrient.batch.domain.Supplement;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplementProductResponse {
    private String bsshNm; // 업소명
    private String prdlstNm; //	품목명
    private String gubun; // 품목구분
    @JsonProperty("H_ITEM_NM")
    private String hItemNm; // 품목유형
    private String lcnsNo; // 인허가번호
    private String evlYr; // 보고년도
    private String prdlstReportNo; // 품목제조보고번호
    private String fyerPrdctnAbrtQy; // 연간생산능력(KG)
    private String prdctnQy; // 생산량(KG)

    public static Supplement createSupplement(SupplementProductResponse response) {
        return new Supplement(response.bsshNm,
                response.prdlstNm,
                response.gubun,
                response.hItemNm,
                response.lcnsNo,
                response.evlYr,
                response.prdlstReportNo,
                response.fyerPrdctnAbrtQy,
                response.prdctnQy);
    }
}
