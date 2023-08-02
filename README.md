# VN PIT HELPER

This is a simple CLI tool to aggregate all of your certificates of personal income tax (PIT) withholding in Vietnam,
hopefully, it will help to do your tax yourself with less irritation. It'll parse the XML formatted certificates
issued by your employers and aggregate them into important metrics that you will have to input manually into the
Vietnamese online tax portal.

Notice that this tool only works with Vietnamese taxpayers and all XML files must use the VND for all amounts of money
stated.

# Disclaimer

ALWAYS check for the end result against your original tax certificates.

If you have too much income sources or not directly deducted income, get a professional tax declaration service, it's
worth it (you've probably earned enough to afford this anyway).

# How to use

- Create a parser configuration file in JSON format, it guides this tool on how to parse each XML file (because up until
  the time I finished this, it seemed like there's no single common format for certificates of PIT withholding in
  Vietnam, so sorry that you have to do this for now). By default, this tool will look for it at `./config.json`. Notice
  that each the value of the mapped keys (all except for "path", which is the absolute path to the XML file) is the
  XPath of the field containing that data excluding the root, which means if the XPath the field is `/A/B/C` then the
  value should be `/B/C`, always remember the leading slash "/". Example:

```json
[
  {
    "path": "CTTTNCN_0315728586_CTT56_BE_2023_E0000015.xml",
    "company": "/Content/ComNames",
    "insurance": "/Content/Insuarance",
    "taxable_income": "/Content/TotalTax",
    "tax_paid": "/Content/Products/Product/Total"
  },
  {
    "path": "Nguyễn Lê Thiện Hiếu_8576768683_CTKT năm 2022.xml",
    "company": "/DLCTu/NDCTu/TCTTNhap/Ten",
    "insurance": "/DLCTu/NDCTu/TTNCNKTru/BHiem",
    "taxable_income": "/DLCTu/NDCTu/TTNCNKTru/TTNCThue",
    "tax_paid": "/DLCTu/NDCTu/TTNCNKTru/SThue"
  }
]
```

- Build the fat jar ```./gradlew build```
- Usage info

```bash 
java -jar build/libs/vn-pit-helper-1.0-SNAPSHOT-standalone.jar -h
```

- Execute the tool
- Check the results with the original certificates if you mapped the fields in each XML file correctly and adjust the
  configuration JSON file if necessary. DO NOT IGNORE THIS STEP!!!