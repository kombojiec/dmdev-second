# dmdev-second

Проект для отработки приобретаемых технологий, в частности:
* сборщик проектов Maven
* модульное и интеграционное тестирование с использованием библиотек JUnit5 & Mockito
* ORM Hibernate
* Spring и его модули

Проект представляет из себя небольшое CRM приложение для учёта работы в библиотеке.   
Основные сущности:
* пользователь системы
* клиент
* печатное издание
* заказ клиента  

Приложение представляет собой маленькую CRM систему в библиотеке
Пользователь системы представляет собой администратора как суперпользователя системы, который имеет доступ ко всей системе и может создавать/редактировать 
других пользователей системы, в частности библиотекаря, который отпускает печатные издания и фиксирует информацию по работе с клиентами и изданиями.
Клиент не является пользователем системы, но информация о клиенте содержится в базе данных, а так же информация о взятых клиентом книгах, которую формирует библиотекарь, который отпускает книги. 

