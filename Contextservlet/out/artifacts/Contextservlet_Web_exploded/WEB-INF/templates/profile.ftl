<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>profile</title>
<body>
<div class="auto">
    <#list users as user>
        <li>${"Логин: "+ user.getLogin()}</li>
        <li>${"Имя: "+ user.getFirstName()}</li>
        <li>${"Фамилия: "+ user.getLastName()}</li>
        <li> ${"Почта: "+ user.getEmail()}</li>
        <li> ${"Телефон: "+ user.getPhone()}</li>
    </#list>
</div>
</body>
</html>