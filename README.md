# Simple_ToDo - простой список дел

Ссылка на скачивание APK-файла (Google drive): https://drive.google.com/file/d/1sn9S7xSemriofRh5nf1669D97eigLZsg/view?usp=sharing

<a href="http://qrcoder.ru" target="_blank"><img src="http://qrcoder.ru/code/?https%3A%2F%2Fdrive.google.com%2Ffile%2Fd%2F1sn9S7xSemriofRh5nf1669D97eigLZsg%2Fview%3Fusp%3Dsharing&4&0" width="180" height="180" border="0" title="QR код"></a>

## Стек приложения
1. ЯП: Kotlin
2. Архитектура: Clean Architecture
3. Паттерн presentation слоя: MVVM
4. Библиотека для работы с SQLite: Room
5. Асинхронность: Coroutines
6. Верстка: XML (заполенение полей DataBinding)
7. Реализация списка: RecyclerView + DiffUtil
8. Тесты: JUnit5 + Mockito

## Описание приложение
Приложение состоит из одной активити и двух фрагментов: фрагмент со списком задач и фрагмент для их редактирования. Задачи можно удалять, добавлять, редактировать, отмечать как выполненные и как важные. Важные задачи становятся красными, выполненные зачеркиваются.

## Скрины

<img src="https://user-images.githubusercontent.com/65513466/211379904-e65ab76c-9aca-437b-b8c1-7f86e44b5cca.png" width="250">    <img src="https://user-images.githubusercontent.com/65513466/211381387-a760ff1d-970c-4706-ba0d-9da369d91161.png" width="250">

<img src="https://user-images.githubusercontent.com/65513466/211381428-ad2755f6-22c7-4574-823a-c9af608b72f2.png" height="250">

<img src="https://user-images.githubusercontent.com/65513466/211381460-93a1f9b2-a101-40e0-9c53-edb5c3e7eb5e.png" height="250">
