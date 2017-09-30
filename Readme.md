# Грамматика:

* `S->V VARGROUPS`
* `V->var`
* `VARGROUPS->VARLIST: WORD; VARGRROUPS`
* `VARGROUPS->eps`
* `VARLIST->WORD, VARLIST`
* `VARLIST->WORD;`
* `WORD->[A-Za-z]WORD`


Нетерминал    | Значение
------------- | -------------
S  | Объявление переменных в Pascal
V | ключевое слово var
VARGROUPS | Разбиение переменных на группы по типам
VARLIST | Список переменных, разделенные запятыми и оканчивающиеся на ;
WORD | Переменная или тип

## Устранение правового ветвления:

* `S->V VARGROUPS`
* `V->var`
* `VARGROUPS->VARLIST: WORD; VARGRROUPS`
* `VARGROUPS->eps`
* `VARLIST->WORDVARLIST'`
* `VARLIST'->, WORDVARLIST'`
* `VARLIST'->;`
* `WORD->[A-Za-z]WORD`

Нетерминал    | Значение
------------- | -------------
S  | Объявление переменных в Pascal
V | ключевое слово var
VARGROUPS | Разбиение переменных на группы по типам
VARLIST | Список переменных, разделенные запятыми и оканчивающиеся на ;
VARLIST' | Продолжение или окончание перечисления переменных
WORD | Переменная или тип


## Множества FIRST и FOLLOW для нетерминалов. 

`c` - символ из [A-Za-z]. 

Нетерминал | FIRST    | FOLLOW
-----------|----------|-------
S          | `v`      |`$`
V          | `v`      |` `
VARGROUPS  | `c`,`eps`|`$`
VARLIST    | `c`      |`:`
VARLIST'   | `,`, `;` |`:`
WORD       | `c`      |`;`,`,`
