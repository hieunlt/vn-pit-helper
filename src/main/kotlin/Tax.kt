import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import java.io.File

data class TaxReport(
    var details: MutableList<TaxData>,
    var companies: MutableList<String?>,
    var totalInsurance: Int,
    var totalTaxableIncome: Int,
    var totalTaxPaid: Int
) {
    constructor() : this(mutableListOf<TaxData>(), mutableListOf<String?>(), 0, 0, 0)

    fun add(data: TaxData) {
        this.details.add(data)
        this.companies.add(data.company)
        this.totalInsurance += data.insurance
        this.totalTaxableIncome += data.taxableIncome
        this.totalTaxPaid += data.taxPaid
    }

    fun export(writer: ObjectWriter, path: String?) {
        path?.let {
            val outputFile = File(it)
            writer.writeValue(outputFile, this)
        } ?: println(writer.writeValueAsString(this))
    }
}

data class TaxData(
    var company: String?, var insurance: Int, var taxableIncome: Int, var taxPaid: Int
) {
    constructor(mapper: XmlMapper, config: ParserConfig) : this() {
        val file = File(config.path)
        val data = mapper.readTree(file)
        this.company = data.at(config.company).asText(null)
        this.insurance = data.at(config.insurance).asInt()
        this.taxableIncome = data.at(config.taxableIncome).asInt()
        this.taxPaid = data.at(config.taxPaid).asInt()
    }

    constructor() : this(null, 0, 0, 0)
}

