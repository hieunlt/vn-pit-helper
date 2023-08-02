import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.File

data class ParserConfig(
    val path: String, val company: String, val insurance: String, val taxableIncome: String, val taxPaid: String
)

fun main(args: Array<String>) {
    val argParser = ArgParser("VN PIT HELPER")
    val input by argParser.option(ArgType.String, shortName = "i", description = "Path to the JSON config file")
        .default("./config.json")
    val output by argParser.option(
        ArgType.String,
        shortName = "o",
        description = "Path to the JSON output file, omit to print output to stdout"
    )
    argParser.parse(args)

    val objectMapper = jacksonObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    val parserConfigs: List<ParserConfig> = objectMapper.readValue(File(input).readText())

    val taxReport = TaxReport()
    parserConfigs.forEach { taxReport.add(TaxData(XmlMapper(), it)) }
    taxReport.export(objectMapper.writerWithDefaultPrettyPrinter(), output)
}