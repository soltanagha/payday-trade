package com.payday.stockms.service;


// Service limit kecir diye islemir
class StockServiceImplTest {
/*
    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private RestTemplate restTemplate;

    String apiKey = "YWDBOAE7KNSN5H12";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLatestStockPrices_Success() {
        String[] symbols = {"AAPL", "MSFT", "GOOGL"};
        for (String symbol : symbols) {
            String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
            String responseString = "{ \"Global Quote\": { \"05. price\": \"100\" } }";
            ResponseEntity<String> response = new ResponseEntity<>(responseString, HttpStatus.OK);
            when(restTemplate.getForEntity(url, String.class)).thenReturn(response);
        }

        List<StockDto> stockPrices = stockService.getLatestStockPrices();

        assertNotNull(stockPrices);
        assertEquals(symbols.length, stockPrices.size());
        for (int i = 0; i < symbols.length; i++) {
            assertEquals(UUID.nameUUIDFromBytes(symbols[i].getBytes()).toString(), stockPrices.get(i).getStockId());
            assertEquals(symbols[i], stockPrices.get(i).getSymbol());
            //assertEquals(100.0, stockPrices.get(i).getPrice());
        }
    }

    @Test
    void getLatestStockPrices_Failed() {
        String symbol = "AAPL";
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(restTemplate.getForEntity(url, String.class)).thenReturn(response);

        List<StockDto> stockPrices = stockService.getLatestStockPrices();

        assertNotNull(stockPrices);
        assertTrue(stockPrices.isEmpty());
    }*/
}
