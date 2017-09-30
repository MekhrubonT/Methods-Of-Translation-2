# Грамматика:

* `S->var VARGROUPS`
* `VARGROUPS->VARLIST: TYPE; VARGRROUPS`
* `VARLIST->VAR, VARLIST`
* `VARLIST->VAR;`
* `TYPE->VAR`
* `VAR->[a-z]*`


Нетерминал    | Значение
------------- | -------------
S  | Объявление переменнgiых в Pascal
VARGROUPS | Разбиений на группы переменных по типам
VARLIST | Список переменных, разделенные запятыми и оканчивающийся на ;
VAR | Переменная
TYPE | Тип

## Устранениие правового ветвления:

* `S->var VARGROUPS`
* `VARGROUPS->VARLIST: TYPE; VARGRROUPS`
* `VARLIST->VARVARLIST'`
* `VARLIST'->, VARLIST'`
* `VARLIST'->;`
* `TYPE->VAR`
* `VAR->[a-z]*`
