package com.bbva.iso8583lib

import com.bbva.iso8583lib.iso.data.Version

class IsoFileTest {
    companion object{
        val DEFAULT_VERSION = Version(1, 0, 0)
        const val DEFAULT_ISO_FIELD_55 = "5F2A02048482021C008407A0000000032010950580800080009A032401089C01009F02060000000900009F03060000000000009F090200839F100706010A03A4A8109F1A0204849F1E0836523635333530349F26082E078F5CEA112DAA9F2701809F3303E0B0C89F34030103029F3501229F4104000000019F530100"
        const val DEFAULT_BYTEARRAY_ISO = "01DF600022000002003038060008818312000000000000090000000019130835010800510001486345556D3371476D5A6F6730303030303030310009303034343531373631048401245F2A02048482021C008407A0000000032010950580800080009A032401089C01009F02060000000900009F03060000000000009F090200839F100706010A03A4A8109F1A0204849F1E0836523635333530349F26082E078F5CEA112DAA9F2701809F3303E0B0C89F34030103029F3501229F4104000000019F5301000069000A50583652363533353034010B42425641525432395F3130020E52544D563235323931313233323915133839333430373632303030303935333935313816013220023530000230330205455A373230303130313030303132453930313030303030363030303030303530303133383030303034184805B90D0FE4118C8EB6E45A57FD296ECEFBAF16B452393435363636443443393943523731334E202020202020202020202020435A34303031353920202020202020202020202020202020202020202020202020202020202020202020202051363036303030333035514B3438F8A7779C3F418690AD0E3C073780C2CFD1C35589227E969DB889BA75549509FE07756777EA0309014634434341344330515230325320"

         const val file: String =
             "{\n" +
                     "  \"versión\": \"1.0.0\",\n" +
                     "  \"fields\": [\n" +
                     "    {\n" +
                     "      \"field\": 0,\n" +
                     "      \"name\": \"Message Type Indicator\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 1,\n" +
                     "      \"name\": \"Bitmap\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 2,\n" +
                     "      \"name\": \"Primary Account number PAN\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 24\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 3,\n" +
                     "      \"name\": \"Processing Code\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 6\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 4,\n" +
                     "      \"name\": \"Amount, transaction\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 12\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 5,\n" +
                     "      \"name\": \"Amount, Settlement\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 12\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 6,\n" +
                     "      \"name\": \"Amount, cardholder billing\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 12\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 7,\n" +
                     "      \"name\": \"Transmission date & time\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 10\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 8,\n" +
                     "      \"name\": \"Amount, Cardholder billing fee\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 9,\n" +
                     "      \"name\": \"CConversion rate, Settlement\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 10,\n" +
                     "      \"name\": \"Conversion rate, cardholder billing\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 11,\n" +
                     "      \"name\": \"Systems trace audit number\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 6\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 12,\n" +
                     "      \"name\": \"Local Tran Time\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 6\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 13,\n" +
                     "      \"name\": \" Local Tran Date\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 14,\n" +
                     "      \"name\": \"Expiration date\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 15,\n" +
                     "      \"name\": \"Settlement date\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 16,\n" +
                     "      \"name\": \"Conversion date\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 17,\n" +
                     "      \"name\": \"Capture date\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 18,\n" +
                     "      \"name\": \"Merchant type\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 19,\n" +
                     "      \"name\": \"Country code, Acquiring institution\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 20,\n" +
                     "      \"name\": \"Country code, Primary account number\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 21,\n" +
                     "      \"name\": \"Country code, Forwarding institution\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 22,\n" +
                     "      \"name\": \"POS Entry Mode\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 23,\n" +
                     "      \"name\": \"Card Sequence Number\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 24,\n" +
                     "      \"name\": \"Function code\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 25,\n" +
                     "      \"name\": \"Message reason code\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 2\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 26,\n" +
                     "      \"name\": \"Card acceptor business code\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 4\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 27,\n" +
                     "      \"name\": \"Approval code length\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 1\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 28,\n" +
                     "      \"name\": \"Date, Reconciliation\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 29,\n" +
                     "      \"name\": \"Reconciliation indicator\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 30,\n" +
                     "      \"name\": \"Amounts, original\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 12\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 31,\n" +
                     "      \"name\": \"Acquirer reference data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 99\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 32,\n" +
                     "      \"name\": \"Acquirer institution ident code\",\n" +
                     "      \"format\": \"VAR_NUMERIC\",\n" +
                     "      \"size\": 11\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 33,\n" +
                     "      \"name\": \"Forwarding institution ident code\",\n" +
                     "      \"format\": \"VAR_NUMERIC\",\n" +
                     "      \"size\": 11\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 34,\n" +
                     "      \"name\": \"Primary account number, extended\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 28\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 35,\n" +
                     "      \"name\": \"Track 2 data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 37\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 36,\n" +
                     "      \"name\": \"Track 3 data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 14\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 37,\n" +
                     "      \"name\": \"Retrieval reference number\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 12\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 38,\n" +
                     "      \"name\": \"Approval code\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 6\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 39,\n" +
                     "      \"name\": \"Response code\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 2\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 40,\n" +
                     "      \"name\": \"Service restriction code\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 41,\n" +
                     "      \"name\": \"Card acceptor terminal identification\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 42,\n" +
                     "      \"name\": \"Card acceptor identification code\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 15\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 43,\n" +
                     "      \"name\": \"Card acceptor name/location\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 99\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 44,\n" +
                     "      \"name\": \"Additional response data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 99\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 45,\n" +
                     "      \"name\": \"Track 1 data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 80\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 46,\n" +
                     "      \"name\": \"Additional data - ISO\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 9999\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 47,\n" +
                     "      \"name\": \"Additional data - national\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 9999\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 48,\n" +
                     "      \"name\": \"Additional Data-Retailer Data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 999\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 49,\n" +
                     "      \"name\": \"Transaction Currency Code\",\n" +
                     "      \"format\": \"FIX_NUMERIC\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 50,\n" +
                     "      \"name\": \"Currency code, Reconciliation\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 51,\n" +
                     "      \"name\": \"Currency code, Cardholder billing\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 3\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 52,\n" +
                     "      \"name\": \"PIN Data\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 16\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 53,\n" +
                     "      \"name\": \"Security related control information\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 48\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 54,\n" +
                     "      \"name\": \"Additional Amounts\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 12\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 55,\n" +
                     "      \"name\": \"Datos de EMV Full Grade\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 256\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 56,\n" +
                     "      \"name\": \"Custom Service Data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 255\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 57,\n" +
                     "      \"name\": \"CV2\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 15\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 58,\n" +
                     "      \"name\": \"Point Redemption\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 255\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 59,\n" +
                     "      \"name\": \"Campaign Data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 999\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 60,\n" +
                     "      \"name\": \" POS Terminal Entry Capability\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 999\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 61,\n" +
                     "      \"name\": \"Logon Data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 256\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 62,\n" +
                     "      \"name\": \"EMV Reference Cryptogram\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 8\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 63,\n" +
                     "      \"name\": \"Transaction Data\",\n" +
                     "      \"format\": \"VAR_CHAR\",\n" +
                     "      \"size\": 999\n" +
                     "    },\n" +
                     "    {\n" +
                     "      \"field\": 64,\n" +
                     "      \"name\": \"Message authentication code field\",\n" +
                     "      \"format\": \"FIX_CHAR\",\n" +
                     "      \"size\": 999\n" +
                     "    }\n" +
                     "  ]\n" +
                     "}"
    }
}
/*
01DF600022000002003038060008818312000000000000090000000019130835010800510001486345556D3371476D5A6F6730303030303030310009303034343531373631048401245F2A02048482021C008407A0000000032010950580800080009A032401089C01009F02060000000900009F03060000000000009F090200839F100706010A03A4A8109F1A0204849F1E0836523635333530349F26082E078F5CEA112DAA9F2701809F3303E0B0C89F34030103029F3501229F4104000000019F5301000069000A50583652363533353034010B42425641525432395F3130020E52544D563235323931313233323915133839333430373632303030303935333935313816013220023530000230330205455A373230303130313030303132453930313030303030363030303030303530303133383030303034184805B90D0FE4118C8EB6E45A57FD296ECEFBAF16B452393435363636443443393943523731334E202020202020202020202020435A34303031353920202020202020202020202020202020202020202020202020202020202020202020202051363036303030333035514B3438F8A7779C3F418690AD0E3C073780C2CFD1C35589227E969DB889BA75549509FE07756777EA0309014634434341344330515230325320


-----------------------------------------------------------
Message type: 0200
-----------------------------------------------------------
FIELD  TYPE       LENGHT    VALUE
 3 -> NUMERIC       6       [000000]
 4 -> AMOUNT       12       [000000090000]
11 -> NUMERIC       6       [000019]
12 -> NUMERIC       6       [130835]
13 -> NUMERIC       4       [0108]
22 -> NUMERIC       3       [051]
23 -> NUMERIC       3       [001]
37 -> ALPHA        12       [HcEUm3qGmZog]
41 -> ALPHA         8       [00000001]
48 -> LLLVAR        9       [004451761]
49 -> NUMERIC       3       [484]
55 -> LLLBIN      124       [5F2A02048482021C008407A0000000032010950580800080009A032401089C01009F02060000000900009F03060000000000009F090200839F100706010A03A4A8109F1A0204849F1E0836523635333530349F26082E078F5CEA112DAA9F2701809F3303E0B0C89F34030103029F3501229F4104000000019F530100]
56 -> LLLBIN       69       [000A50583652363533353034010B42425641525432395F3130020E52544D563235323931313233323915133839333430373632303030303935333935313816013220023530]
60 -> LLLVAR        2       [03]
63 -> LLLBIN      205       [455A373230303130313030303132453930313030303030363030303030303530303133383030303034184805B90D0FE4118C8EB6E45A57FD296ECEFBAF16B452393435363636443443393943523731334E202020202020202020202020435A34303031353920202020202020202020202020202020202020202020202020202020202020202020202051363036303030333035514B3438F8A7779C3F418690AD0E3C073780C2CFD1C35589227E969DB889BA75549509FE07756777EA0309014634434341344330515230325320]


 */