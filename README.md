# Объект тестирования:
https://pokeapi.co/

Чек-лист:
1. Проверить, что у покемона rattata, в отличие от покемона pidgeotto, меньше вес и есть умение
   (ability) побег (run-away)
2. Проверить ограничение списка (limit) покемонов и то, что у каждого покемона в
   ограниченном списке есть имя (name)

## Case 1: сравнение покемонов rattata и pidgeotto

Шаги:

1. Сделать GET-запрос  с параметром name = rattata
```
https://pokeapi.co/api/v2/pokemon/{name}/
```
2. Полученный ответ десериализовать из json в POJO
3. Сделать GET-запрос с параметром name = pidgeotto
```
https://pokeapi.co/api/v2/pokemon/{name}/
```
4. Полученный ответ десериализовать из json в POJO

Ожидаемый результат:

При каждом GET HTTP Status: 200 OK 

У покемона rattata вес (weight) меньше чем у покемона pidgeotto

У покемона rattata есть умение ability с name = run-away

У покемона pidgeotto нет умения ability с name = run-away

## Case 2: проверка списка покемонов

Шаги:

1. Сделать GET-запрос
```
https://pokeapi.co/api/v2/pokemon/?limit=15
```
2. Десериализовать ответ из json в POJO

Ожидаемый результат:

При каждом GET HTTP Status: 200 OK 

Значение поля next содержит limit=15, где number целое число и не null

Пример:
```
"next": "https://pokeapi.co/api/v2/pokemon/?offset=20&limit=15"
```
Значение каждого name в списке results не является null

Длина списка results равна значению number в limit=15


