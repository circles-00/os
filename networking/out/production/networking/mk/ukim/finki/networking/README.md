Потребно е да имплементирате сервер-клиент сценарио сo користење на TCP протоколот. Клиентите ги филтрираат сите датотеки во даден именик кои се помали од 20 KB. Вкупниот број на датотеки кои го исполнуваат условот, треба да се прати до сервер. Серверот кога ќе прими порака од одреден клиент, пораката ја запишува во датотеката `results.txt` која постои локално кај него. Секоја информација од секој клиент ја чува во нова линија во истиот документ, во следниот формат:

`<IP-address-of-the-client> <port-of-the-client> <number-of-files>`

Потребно е да овозможите праќање на податоци од повеќе клиенти едновремено. Серверот слуша на порта `4498`. Редоследот на праќање на податоците од клиентите не е важен.