package com.blogspot.mikelaud.data;

public class SymbolsNyseIndices extends SymbolsAbstract {

	private final String[] SYMBOLS =
	{	"AD-NYSE"
	,	"AXSVTN"
	,	"AXTWEN"
	,	"COW.IV"
	,	"DJP.IV"
	,	"DMT"
	,	"EXV"
	,	"FEU"
	,	"FEZ"
	,	"FNAR"
	,	"FXA.NV"
	,	"FXB.NV"
	,	"FXC.NV"
	,	"FXE.NV"
	,	"FXE.TC"
	,	"FXF.NV"
	,	"FXM.NV"
	,	"FXS.NV"
	,	"FXY.NV"
	,	"GAZ.IV"
	,	"GLD.IV"
	,	"IDV.NV"
	,	"INDU"
	,	"JJA.IV"
	,	"JJC.IV"
	,	"JJE.IV"
	,	"JJG.IV"
	,	"JJM.IV"
	,	"JJN.IV"
	,	"MLEIFCTX"
	,	"MZG.NV"
	,	"MZN.NV"
	,	"MZO.NV"
	,	"NYE.ID"
	,	"NYID"
	,	"NYK.ID"
	,	"NYP.ID"
	,	"PAF.IV"
	,	"PAF.NV"
	,	"PEF.IV"
	,	"PEF.NV"
	,	"PJO.IV"
	,	"PJO.NV"
	,	"PXF.IV"
	,	"PXF.NV"
	,	"TICK-NYSE"
	,	"TIKI"
	,	"TRIN-NYSE"
	,	"USTLBD"
	,	"USTTEN"
	,	"VOLI"
	,	"VOL-NYSE"
	,	"XPJ.IV"
	};

	@Override
	protected String[] getSymbols() {
		return SYMBOLS;
	}
	
	public String getSecurityType() {
		return "IND";
	}
	
	public SymbolsNyseIndices() {
		// void
	}

}
