package Ivan.com;

public class Main {
    private static final int SIZE = 10000000;
    private static final int HALFSIZE = SIZE / 2;

    public static void main(String[] args) {
        //System.out.println("Current time in milliseconds: " + System.currentTimeMillis());

        runOneThread();
        runTwoThreads();
    }

    public static float[] calculation(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2F + i / 5) * Math.cos(0.2F + i / 5) * Math.cos(0.4F + i / 2));
        } //пробегаемся по массиву и преобразовываем его элементы по формуле
        return array; 
    }

    public static void runOneThread() {
        float[] array = new float[SIZE]; //заводим массив дробных чисел float
        for (int i = 0; i < array.length; i++) {
            array[i] = 1.0F; //и заполняем его единицами
        }
        long a = System.currentTimeMillis(); //метод currentTimeMillis возвращает сколько времени прошло от
        // полудня 1 января 1970 г до текущего момента времени, long-т.к. значение возвращаемое не умещается в int
        calculation(array); //преобразовываем массив согласну методу calculation
        System.out.println("One thread method lasts for: " + (System.currentTimeMillis() - a));
        //выводим в консоль время текущее после заполнения массива минус время текущее от заполнения массива единицами
        //т.е. выводим время, которое занял метод по заполнению массива
    }

    public static void runTwoThreads() { //попытаемся выполнить ту же задачу но через 2 потока и посмотрим какой метод
        //справится быстрее
        float[] array = new float[SIZE]; //исходный массив разбиваем на два массива, в двух потоках
        // высчитываем новые значения и потом склеиваем эти массивы обратно в один.
        float[] array1 = new float[HALFSIZE]; //2 массива которые мы потом склеим обратно в один
        float[] array2 = new float[HALFSIZE];
        for (int i = 0; i < array.length; i++) {
            array[i] = 1.0F; //заполнили начальный массив единицами
        }
        long a = System.currentTimeMillis(); //начали засекать время
        System.arraycopy(array, 0, array1, 0, HALFSIZE); //(откуда копировать,с какого индекса
        //копируем, куда отправлять, на какую позицию и сколько ячеек)
        System.arraycopy(array, HALFSIZE, array2, 0, HALFSIZE); //копируем вторую половину исходного
        //массива в новый массив2

        CalculationThread calculationThread = new CalculationThread(array1); //объявляем объект класса CalculationThread-
        //класс в котором будет происходить преобразование половины массива, конструктору подаем в параметры первую
        // половину исходного массива
        calculationThread.start(); //метод start запускает новый поток

        CalculationThread calculationThread1 = new CalculationThread(array2); //новый объект того же класса которому
        //мы в параметры конструктора уже подаем вторую половину исходного массива
        calculationThread1.start(); //метод start запускает новый поток

        System.arraycopy(array1, 0, array, 0, HALFSIZE); //заносим заново в первую половину array
        // результат преобразования array1 в первом потоке
        System.arraycopy(array2, 0, array, HALFSIZE, HALFSIZE);
        System.out.println("Two threads last for: " + (System.currentTimeMillis() - a)); //выводим на экран
        //время текущее за вычетом начала отсчета времени, (перед тем как мы создавали 2 потока)
        //т.е. данная разница равна времени преобразования целого массива, которое можно сравнить с тем что
        //мы делали в одном потоке в методе runOneThread()
    }
}
