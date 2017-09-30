# Грамматика:

* `S->var VARGROUPS`
* `VARGROUPS->VARLIST: TYPE; VARGRROUPS`
* `VARLIST->VAR, VARLIST`
* `VARLIST->VAR;`
* `TYPE->VAR`
* `VAR->[a-z]*`


Нетерминал    | Значение
------------- | -------------
S  | Объявление переменных в Pascal
VARGROUPS | Разбиение переменных на группы по типам
VARLIST | Список переменных, разделенные запятыми и оканчивающиеся на ;
VAR | Переменная
TYPE | Тип

## Устранение правового ветвления:

* `S->var VARGROUPS`
* `VARGROUPS->VARLIST: TYPE; VARGRROUPS`
* `VARLIST->VARVARLIST'`
* `VARLIST'->, VARVARLIST'`
* `VARLIST'->;`
* `TYPE->VAR`
* `VAR->[a-z]*`

Нетерминал    | Значение
------------- | -------------
S  | Объявление переменных в Pascal
VARGROUPS | Разбиение переменных на группы по типам
VARLIST | Список переменных, разделенные запятыми и оканчивающиеся на ;
VARLIST' | Продолжение или окончание перечисления переменных
VAR | Переменная
TYPE | Тип

