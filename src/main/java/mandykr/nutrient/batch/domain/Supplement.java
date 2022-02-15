package mandykr.nutrient.batch.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Supplement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUPPLEMENT_ID")
    private Long id;

    // base
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLEMENT_CATEGORY_ID")
	private SupplementCategoryBase supplementCategoryBase;
	private boolean deleted = Boolean.FALSE;

    // common
	private String lcnsNo;	// 인허가번호
	private String bsshNm;	// 업소_명
	private String prdlstReportNo;	// 품목제조번호
	private String prdlstNm;	// 품목_명

    // product
	private String gubun; // 품목구분
	private String hItemNm; // 품목유형 = category name
	private String evlYr; // 보고년도
	private String fyerPrdctnAbrtQy; // 연간생산능력(KG)
	private String prdctnQy; // 생산량(KG)

	// explain
	private String prmsDt;	// 허가_일자
	private String pogDaycnt;	// 유통기한_일수
	private String dispos;	// 성상
	private String ntkMthd;	// 섭취방법
	@Lob
	private String primaryFnclty;	// 주된기능성
	@Lob
	private String iftknAtntMatrCn;	// 섭취시주의사항
	private String cstdyMthd;	// 보관방법
	private String prdlstCdnm;	// 유형
	@Lob
	private String stdrStnd;	// 기준규격
	private String hiengLntrtDvsNm;	// 고열량저영양여부
	private String production;	// 생산종료여부
	private String childCrtfcYn;	// 어린이기호식품품질인증여부
	private String prdtShapCdNm;	// 제품형태
	private String frmlcMtrqlt; 	// 포장재질
	private String rawmtrlNm;	// 품목유형(기능지표성분)
	private String indutyCdNm;	//업종
	private String lastUpdtDtm;	// 최종수정일자
	@Lob
	private String indivRawmtrlNm; 	// 기능성 원재료
	@Lob
	private String etcRawmtrlNm;	// 기타 원재료
	private String capRawmtrlNm;	// 캡슐 원재료

	public Supplement(
			String lcnsNo, String bsshNm, String prdlstReportNo, String prdlstNm,
		    String gubun, String hItemNm, String evlYr, String fyerPrdctnAbrtQy, String prdctnQy) {
		this.lcnsNo = lcnsNo;
		this.bsshNm = bsshNm;
		this.prdlstReportNo = prdlstReportNo;
		this.prdlstNm = prdlstNm;
		this.gubun = gubun;
		this.hItemNm = hItemNm;
		this.evlYr = evlYr;
		this.fyerPrdctnAbrtQy = fyerPrdctnAbrtQy;
		this.prdctnQy = prdctnQy;
	}

	public Supplement(
			String lcnsNo, String bsshNm, String prdlstReportNo, String prdlstNm,
			String prmsDt, String pogDaycnt, String dispos, String ntkMthd, String primaryFnclty,
			String iftknAtntMatrCn, String cstdyMthd, String prdlstCdnm, String stdrStnd,
			String hiengLntrtDvsNm, String production, String childCrtfcYn, String prdtShapCdNm,
			String frmlcMtrqlt, String rawmtrlNm, String indutyCdNm, String lastUpdtDtm,
			String indivRawmtrlNm, String etcRawmtrlNm, String capRawmtrlNm) {
		this.lcnsNo = lcnsNo;
		this.bsshNm = bsshNm;
		this.prdlstReportNo = prdlstReportNo;
		this.prdlstNm = prdlstNm;
		this.prmsDt = prmsDt;
		this.pogDaycnt = pogDaycnt;
		this.dispos = dispos;
		this.ntkMthd = ntkMthd;
		this.primaryFnclty = primaryFnclty;
		this.iftknAtntMatrCn = iftknAtntMatrCn;
		this.cstdyMthd = cstdyMthd;
		this.prdlstCdnm = prdlstCdnm;
		this.stdrStnd = stdrStnd;
		this.hiengLntrtDvsNm = hiengLntrtDvsNm;
		this.production = production;
		this.childCrtfcYn = childCrtfcYn;
		this.prdtShapCdNm = prdtShapCdNm;
		this.frmlcMtrqlt = frmlcMtrqlt;
		this.rawmtrlNm = rawmtrlNm;
		this.indutyCdNm = indutyCdNm;
		this.lastUpdtDtm = lastUpdtDtm;
		this.indivRawmtrlNm = indivRawmtrlNm;
		this.etcRawmtrlNm = etcRawmtrlNm;
		this.capRawmtrlNm = capRawmtrlNm;
	}

	public static Supplement createSupplementWithProduct(
			String lcnsNo, String bsshNm, String prdlstReportNo, String prdlstNm,
			String gubun, String hItemNm, String evlYr, String fyerPrdctnAbrtQy, String prdctnQy) {
		return new Supplement(lcnsNo, bsshNm, prdlstReportNo, prdlstNm, gubun, hItemNm, evlYr, fyerPrdctnAbrtQy, prdctnQy);
	}

	public static Supplement createSupplementWithExplain(
			String lcnsNo, String bsshNm, String prdlstReportNo, String prdlstNm,
	        String prmsDt, String pogDaycnt, String dispos, String ntkMthd, String primaryFnclty,
	        String iftknAtntMatrCn, String cstdyMthd, String prdlstCdnm, String stdrStnd,
	        String hiengLntrtDvsNm, String production, String childCrtfcYn, String prdtShapCdNm,
	        String frmlcMtrqlt, String rawmtrlNm, String indutyCdNm, String lastUpdtDtm,
	        String indivRawmtrlNm, String etcRawmtrlNm, String capRawmtrlNm) {
		return new Supplement(lcnsNo, bsshNm, prdlstReportNo, prdlstNm, prmsDt, pogDaycnt, dispos, ntkMthd, primaryFnclty,
				iftknAtntMatrCn, cstdyMthd, prdlstCdnm, stdrStnd, hiengLntrtDvsNm, production, childCrtfcYn, prdtShapCdNm,
				frmlcMtrqlt, rawmtrlNm, indutyCdNm, lastUpdtDtm, indivRawmtrlNm, etcRawmtrlNm, capRawmtrlNm);
	}

	public void editExplain(Supplement supplement) {
		this.prmsDt = supplement.getPrmsDt();
		this.pogDaycnt = supplement.getPogDaycnt();
		this.dispos = supplement.getDispos();
		this.ntkMthd = supplement.getNtkMthd();
		this.primaryFnclty = supplement.getPrimaryFnclty();
		this.iftknAtntMatrCn = supplement.getIftknAtntMatrCn();
		this.cstdyMthd = supplement.getCstdyMthd();
		this.prdlstCdnm = supplement.getPrdlstCdnm();
		this.stdrStnd = supplement.getStdrStnd();
		this.hiengLntrtDvsNm = supplement.getHiengLntrtDvsNm();
		this.production = supplement.getProduction();
		this.childCrtfcYn = supplement.getChildCrtfcYn();
		this.prdtShapCdNm = supplement.getPrdtShapCdNm();
		this.frmlcMtrqlt = supplement.getFrmlcMtrqlt();
		this.rawmtrlNm = supplement.getRawmtrlNm();
		this.indutyCdNm = supplement.getIndutyCdNm();
		this.lastUpdtDtm = supplement.getLastUpdtDtm();
		this.indivRawmtrlNm = supplement.getIndivRawmtrlNm();
		this.etcRawmtrlNm = supplement.getEtcRawmtrlNm();
		this.capRawmtrlNm = supplement.getCapRawmtrlNm();
	}

	public void addCategory(SupplementCategoryBase category) {
		this.supplementCategoryBase = category;
	}
}
