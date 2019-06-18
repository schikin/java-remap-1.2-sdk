# java-remap-sdk 1.2

Данная библиотека предназначена для удобной работы с API Remap версии 1.2 МоегоСклада. Она содержит набор функций, позволяющий совершать базовые операции над сущностями (создание, чтение, изменение, удаление).

## Пример работы

### Начало работы

Основной класс, посредством которого осуществляется работа с SDK: `com.lognex.api.LognexApi`

Для того, чтобы начать работу, требуется создать экземпляр этого класса, передав в конструктор поля адрес хоста API, флаг принудительного соединения по https, а также логин в формате `[имя пользователя]@[название компании]` и пароль для доступа к API:

```
import com.lognex.api.LognexApi;

LognexApi api = new LognexApi("online.moysklad.ru", true, "[API_LOGIN]", "[API_PASSWORD]");
```

### Работа с сущностями

Для доступа к эндпоинтам отдельных сущностей используется метод ```com.lognex.api.LognexApi.entity()```, который возвращает базовый клиент для сущностей. Этот объект позволяет получить клиент для работы с конкретной сущностью. Например, чтобы получить список всех контрагентов, достаточно выполнить следующий код:

```
ListEntity<CounterpartyEntity> counterpartyList = api.entity().counterparty().get();
```

Чтобы отправить запрос к API на создание сущности, достаточно создать объект класса, заполнить необходимые поля, и затем при помощи соответствующего клиента вызвать метод `post()`, передав в качестве параметра созданный объект:

```
ProductEntity product = new ProductEntity();
product.setName("Новый продукт");
api.entity().product().post(product);
```

После выполнения кода и при успешном создании объект `product` будет заполнен полями, полученными из ответа API (`id`, `href` и др.). 

Для изменения уже созданной сущности используется метод `put()` клиента сущности, где в качестве параметров выступают либо сущность с измененными полями (должен быть установлен `id`), либо `id` и сущность с измененными полями:

```
product.setDescription("Описание продукта");
api.entity().product().put(product);
```

Полностью аналогично производится работа с остальными методами. Работа с вложенными сущностями производится при помощи специальных методов клиентов сущностей (например, метод получения позиций документов `getPositions(String, ApiParam...)`).

### Экспорт документов

Экспорт документов осуществляется с использованием шаблонов. Чтобы получить список доступных шаблонов для данного документа, нужно у соответствующего клиента вызвать цепочку методов `metadata().embeddedtemplate()`. Например, получение списка шаблонов экспорта для отгрузки:
```
ListEntity<TemplateEntity> templates = api.entity().demand().metadata().embeddedtemplate();
```

Для создания печатной формы по выбранному шаблону используется метод клиента документа `export()`. В самом простом виде вызов метода выглядит следующим образом:
```
File exportFile = new File("demandExport.pdf");
api.entity().demand().export("{demand_id}", templates.getRows().get(0), exportFile);
```

В результате вызова метода будет создан файл `demandExport.pdf`, содержащий экспортированную отгрузку с `id = {demand_id}` по первому доступному шаблону.

### Дальнейшее знакомство

Список доступных в Remap API 1.2 методов и сущностей, а также накладываемые ограничения по работе с API можно узнать в документации по ссылке https://dev.moysklad.ru/

## Запуск тестов

Для запуска тестов нужно в переменные среды прописать корректные данные для доступа к API:  
* `API_HOST` — хост API (например `online.moysklad.ru`)_
* `API_LOGIN` — логин в формате `[имя пользователя]@[название компании]`
* `API_PASSWORD` — пароль