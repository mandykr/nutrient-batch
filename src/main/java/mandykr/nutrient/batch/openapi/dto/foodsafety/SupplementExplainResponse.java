package mandykr.nutrient.batch.openapi.dto.foodsafety;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import mandykr.nutrient.batch.domain.Supplement;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplementExplainResponse {
    private String lcnsNo;	// 인허가번호
    private String bsshNm;	// 업소_명
    private String prdlstReportNo;	// 품목제조번호
    private String prdlstNm;	// 품목_명
    private String prmsDt;	// 허가_일자
    private String pogDaycnt;	// 유통기한_일수
    private String dispos;	// 성상
    private String ntkMthd;	// 섭취방법
    private String primaryFnclty;	// 주된기능성
    private String iftknAtntMatrCn;	// 섭취시주의사항
    private String cstdyMthd;	// 보관방법
    private String prdlstCdnm;	// 유형
    private String stdrStnd;	// 기준규격
    private String hiengLntrtDvsNm;	// 고열량저영양여부
    private String production;	// 생산종료여부
    private String childCrtfcYn;	// 어린이기호식품품질인증여부
    private String prdtShapCdNm;	// 제품형태
    private String frmlcMtrqlt; 	// 포장재질
    private String rawmtrlNm;	// 품목유형(기능지표성분)
    private String indutyCdNm;	//업종
    private String lastUpdtDtm;	// 최종수정일자
    private String indivRawmtrlNm; 	// 기능성 원재료
    private String etcRawmtrlNm;	// 기타 원재료
    private String capRawmtrlNm;	// 캡슐 원재료

    public static Supplement createSupplement(SupplementExplainResponse response) {
        return Supplement.createSupplementWithExplain(
                response.lcnsNo,
                response.bsshNm,
                response.prdlstReportNo,
                response.prdlstNm,
                response.prmsDt,
                response.pogDaycnt,
                response.dispos,
                response.ntkMthd,
                response.primaryFnclty,
                response.iftknAtntMatrCn,
                response.cstdyMthd,
                response.prdlstCdnm,
                response.stdrStnd,
                response.hiengLntrtDvsNm,
                response.production,
                response.childCrtfcYn,
                response.prdtShapCdNm,
                response.frmlcMtrqlt,
                response.rawmtrlNm,
                response.indutyCdNm,
                response.lastUpdtDtm,
                response.indivRawmtrlNm,
                response.etcRawmtrlNm,
                response.capRawmtrlNm);
    }
}
