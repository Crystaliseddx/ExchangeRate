# Проект “Обмен валют”
<p>Описание и ТЗ проекта доступно по адресу: https://zhukovsd.github.io/java-backend-learning-course/Projects/CurrencyExchange/</p>
Приложение доступно по адресу: http://62.109.17.145:8080/CurrencyExchange-1.0/ <br>

http://62.109.17.145:8080/CurrencyExchange-1.0/currencies <br>
http://62.109.17.145:8080/CurrencyExchange-1.0/exchangeRates <br>
http://62.109.17.145:8080/CurrencyExchange-1.0/currency/* <br>
http://62.109.17.145:8080/CurrencyExchange-1.0/exchangeRate/* <br>
http://62.109.17.145:8080/CurrencyExchange-1.0/exchange <br>

<h2>REST API для описания валют и обменных курсов. </h2> Позволяет просматривать и редактировать списки валют и обменных курсов, и совершать расчёт конвертации произвольных сумм из одной валюты в другую. </p>
<p>Веб-интерфейс для проекта не подразумевается.</p>

<h2>Мотивация проекта: </h2>
REST API - правильное именование ресурсов, использование HTTP кодов ответа <br>
SQL - базовый синтаксис, создание таблиц <br>

<h2>Используемый стэк </h2>
Java <br>
Maven<br>
Java сервлеты<br>
HTTP - GET и POST запросы, коды ответа<br>
REST API, JSON<br>
Базы данных - SQLite, JDBC<br>
Деплой - облачный хостинг, командная строка Linux, Tomcat<br>

<h2>Валюты</h2>
<h3>GET /currencies </h3>
Получение списка валют. <br>
Пример ответа: 

```JSON
[
    {
        "id": 1,
        "code": "USD",
        "name": "US Dollar",
        "sign": "$"
    },
    {
        "id": 2,
        "code": "RUB",
        "name": "Russian Ruble",
        "sign": "₽"
    }
]
```

HTTP коды ответов: <br>
Успех - 200 <br>
В БД отсутствуют валюты - 404 <br>
База данных недоступна - 500 <br>


<h3>GET /currency/EUR</h3>
Получение конкретной валюты.<br>
Пример ответа:

```JSON
{
    "id": 5,
    "code": "EUR",
    "name": "Euro",
    "sign": "€"
}
```

HTTP коды ответов: <br>
Успех - 200 <br>
Код валюты отсутствует в адресе - 400 <br>
Валюта не найдена в БД - 404 <br>
База данных недоступна - 500 <br>

<h3>POST /currencies</h3>
Добавление новой валюты в базу. Данные передаются в теле запроса в виде application/json. <br>
Пример запроса: 

```JSON
{
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```
Пример Ответа: 

```JSON
{
    "id": 5,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

HTTP коды ответов: <br>
Успех - 200 <br>
Предоставлены некорректные данные для внесения валюты в БД - 400 <br>
Валюта с таким кодом уже существует - 409 <br>
База данных недоступна - 500 <br>

<h2>Обменные курсы</h2>
<h3>GET /exchangeRates</h3>
Получение списка всех обменных курсов. <br>
Пример ответа: 

```JSON
[
    {
        "id": 1,
        "baseCurrency": {
            "id": 1,
            "code": "USD",
            "name": "US Dollar",
            "sign": "$"
        },
        "targetCurrency": {
            "id": 2,
            "code": "RUB",
            "name": "Russian Ruble",
            "sign": "₽"
        },
        "rate": 99.995
    },
    {
        "id": 2,
        "baseCurrency": {
            "id": 1,
            "code": "USD",
            "name": "US Dollar",
            "sign": "$"
        },
        "targetCurrency": {
            "id": 3,
            "code": "KRW",
            "name": "Won",
            "sign": "₩"
        },
        "rate": 1330.6
    }
]
```

HTTP коды ответов: <br>
Успех - 200 <br>
В БД отсутствуют обменные курсы - 404 <br>
База данных недоступна - 500 <br>

<h3>GET /exchangeRate/USDRUB</h3>
Получение конкретного обменного курса. Валютная пара задаётся идущими подряд кодами валют в адресе запроса.  <br>
Пример ответа:

```JSON
{
    "id": 1,
    "baseCurrency": {
        "id": 1,
        "code": "USD",
        "name": "US Dollar",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "code": "RUB",
        "name": "Russian Ruble",
        "sign": "₽"
    },
    "rate": 99.995
}
```

HTTP коды ответов: <br>
Успех - 200 <br>
Коды валют пары отсутствуют в адресе - 400 <br>
Курс обмена для указанных валют отсутствует в БД - 404 <br>
База данных недоступна - 500 <br>

<h3>POST /exchangeRates</h3>
Добавление нового обменного курса в базу. Данные передаются в теле запроса в виде application/json. <br>
Пример запроса: 

```JSON
{
    "baseCurrency": {
        "code": "USD"
    },
    "targetCurrency": {
        "code": "RUB"
    },
    "rate": 99.995
}
```
Пример ответа:

```JSON
{
    "id": 1,
    "baseCurrency": {
        "id": 1,
        "code": "USD",
        "name": "US Dollar",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "code": "RUB",
        "name": "Russian Ruble",
        "sign": "₽"
    },
    "rate": 99.995
}
```

HTTP коды ответов: <br>
Успех - 200 <br>
Предоставлены некорректные данные для внесения валютного курса в БД - 400 <br>
Базовая и целевая валюты отсутствуют в БД - 404 <br>
Валютная пара с таким кодом уже существует - 409 <br>
База данных недоступна - 500 <br>

<h3>PATCH /exchangeRate/USDEUR</h3>
Обновление существующего в базе обменного курса. <br>
Валютная пара задаётся идущими подряд кодами валют в адресе запроса. Данные передаются в теле запроса в виде application/json.  <br>
Пример запроса: 

```JSON
{
    "rate": 0.9121
}
```

Пример ответа:

```JSON
{
    "id": 4,
    "baseCurrency": {
        "id": 1,
        "code": "USD",
        "name": "US Dollar",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 5,
        "code": "EUR",
        "name": "Euro",
        "sign": "€"
    },
    "rate": 0.9121
}
```



HTTP коды ответов: <br>
Успех - 200 <br>
Предоставлены некорректные данные для обновления валютного курса в БД - 400 <br>
Курс обмена для указанных валют отсутствует в БД - 404 <br>
База данных недоступна - 500 <br>

<h2>Обмен валюты</h2> 
<h3>GET /exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=AMOUNT</h3>
Расчёт перевода определённого количества средств из одной валюты в другую. <br> 
Пример запроса - GET /exchange?from=USD&to=RUB&amount=10. <br>
Пример ответа:

```JSON
{
    "baseCurrency": {
        "id": 1,
        "code": "USD",
        "name": "US Dollar",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "code": "RUB",
        "name": "Russian Ruble",
        "sign": "₽"
    },
    "rate": 99.995000,
    "amount": 10.000000,
    "convertedAmount": 999.950000
}
```

HTTP коды ответов: <br>
Успех - 200 <br>
Неккоректные параметры запроса - 400 <br>
Курс обмена для указанных валют отсутствует в БД - 404 <br>
База данных недоступна - 500 <br>

<p>Получение курса для обмена может пройти по одному из трёх сценариев. Допустим, совершаем перевод из валюты A в валюту B:<br>
1) В таблице ExchangeRates существует валютная пара AB - берём её курс <br>
2) В таблице ExchangeRates существует валютная пара BA - берем её курс, и считаем обратный, чтобы получить AB <br>
3) В таблице ExchangeRates существуют валютные пары USD-A и USD-B - вычисляем из этих курсов курс AB <br> </p>
