package mandykr.nutrient.batch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Supplement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
    private Long id;

    private String bsshNm; // 업소명
	private String prdlstNm; //	품목명
	private String gubun; // 품목구분
	private String hItemNm; // 품목유형
	private String lcnsNo; // 인허가번호
	private String evlYr; // 보고년도
	private String prdlstReportNo; // 품목제조보고번호
	private String fyerPrdctnAbrtQy; // 연간생산능력(KG)
	private String prdctnQy; // 생산량(KG)

	public Supplement(String bsshNm,
					  String prdlstNm,
					  String gubun,
					  String hItemNm,
					  String lcnsNo,
					  String evlYr,
					  String prdlstReportNo,
					  String fyerPrdctnAbrtQy,
					  String prdctnQy) {
		this.bsshNm = bsshNm;
		this.prdlstNm = prdlstNm;
		this.gubun = gubun;
		this.hItemNm = hItemNm;
		this.lcnsNo = lcnsNo;
		this.evlYr = evlYr;
		this.prdlstReportNo = prdlstReportNo;
		this.fyerPrdctnAbrtQy = fyerPrdctnAbrtQy;
		this.prdctnQy = prdctnQy;
	}
}
