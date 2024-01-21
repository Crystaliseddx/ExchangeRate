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
<ul>
<li>REST API - правильное именование ресурсов, использование HTTP кодов ответа</li>
<li>SQL - базовый синтаксис, создание таблиц</li>
</ul>

<h2>Используемые технологии </h2>
<ul>
<li>Java </li>
<li>Java servlets </li>
<li>Maven </li>
<li>HTTP - GET и POST запросы, коды ответа </li>
<li>REST API, JSON </li>
<li>Базы данных - SQLite, JDBC </li>
<li>Деплой - облачный хостинг, командная строка Linux, Tomcat </li>
</ul>

<h2>Валюты</h2>
<h3>GET /currencies  - получение списка валют</h3>
<p></p>
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

HTTP коды ответов: 
<ul>
<li>Успех - 200</li>
<li>В БД отсутствуют валюты - 404</li>
<li>База данных недоступна - 500</li>
</ul>

<h3>GET /currency/EUR - получение конкретной валюты</h3>
Пример ответа:

```JSON
{
    "id": 5,
    "code": "EUR",
    "name": "Euro",
    "sign": "€"
}
```

HTTP коды ответов:
<ul>
<li>Успех - 200</li>
<li>Код валюты отсутствует в адресе - 400</li>
<li>Валюта не найдена в БД - 404</li>
<li>База данных недоступна - 500</li>
</ul>

<h3>POST /currencies - добавление новой валюты в базу</h3>
<p>Данные передаются в теле запроса в виде application/json</p>
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

HTTP коды ответов: 
<ul>
<li>Успех - 200</li>
<li>Предоставлены некорректные данные для внесения валюты в БД - 400</li>
<li>Валюта с таким кодом уже существует - 409</li>
<li>База данных недоступна - 500</li>
</ul>

<h2>Обменные курсы</h2>
<h3>GET /exchangeRates - получение списка всех обменных курсов</h3>
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

HTTP коды ответов: 
<ul>
<li>Успех - 200</li>
<li>В БД отсутствуют обменные курсы - 404</li>
<li>База данных недоступна - 500</li>
</ul>

<h3>GET /exchangeRate/USDRUB - получение конкретного обменного курса</h3>
<p> Валютная пара задаётся идущими подряд кодами валют в адресе запроса </p>
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

HTTP коды ответов: 
<ul>
<li>Успех - 200</li>
<li>Коды валют пары отсутствуют в адресе - 400</li>
<li>Курс обмена для указанных валют отсутствует в БД - 404</li>
<li>База данных недоступна - 500</li>
</ul>

<h3>POST /exchangeRates - Добавление нового обменного курса в базу</h3>
<p>Данные передаются в теле запроса в виде application/json</p>
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

HTTP коды ответов: 

<ul>
<li>Успех - 200</li>
<li>Предоставлены некорректные данные для внесения валютного курса в БД - 400</li>
<li>Базовая и целевая валюты отсутствуют в БД - 404</li>
<li>Валютная пара с таким кодом уже существует - 409</li>
<li>База данных недоступна - 500</li>
</ul>

<h3>PATCH /exchangeRate/USDEUR - обновление существующего в базе обменного курса</h3>
<p>Валютная пара задаётся идущими подряд кодами валют в адресе запроса. Данные передаются в теле запроса в виде application/json </p>
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



HTTP коды ответов: 
<ul>
<li>Успех - 200</li>
<li>Предоставлены некорректные данные для обновления валютного курса в БД - 400</li>
<li>Курс обмена для указанных валют отсутствует в БД - 404</li>
<li>База данных недоступна - 500</li>
</ul>

<h2>Обмен валюты</h2> 
<h3>GET /exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=AMOUNT - расчёт перевода определённого количества средств из одной валюты в другую</h3>

<p>Пример запроса - GET /exchange?from=USD&to=RUB&amount=10 </p>
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

HTTP коды ответов: 
<ul>
<li>Успех - 200</li>
<li>Неккоректные параметры запроса - 400</li>
<li>Курс обмена для указанных валют отсутствует в БД - 404</li>
<li>База данных недоступна - 500</li>
</ul>

<p>Получение курса для обмена может пройти по одному из трёх сценариев. Допустим, совершаем перевод из валюты A в валюту B:<br>
<ol>
<li>В таблице ExchangeRates существует валютная пара AB - берём её курс</li>
<li>В таблице ExchangeRates существует валютная пара BA - берем её курс, и считаем обратный, чтобы получить AB</li>
<li>В  таблице ExchangeRates существуют валютные пары USD-A и USD-B - вычисляем из этих курсов курс AB</li>
</ol>
