<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>profile</title>
</head>
<body>
<div>
    <a>dsadsada</a>
    <#list products as product>
        <li>${"Название товара: "+ product.getName()}</li>
        <li>${"Цена: "+ product.getPrice()}</li>
    </#list>
</div>
</body>
</html>