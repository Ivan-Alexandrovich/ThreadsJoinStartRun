package Ivan.com;

public class CalculationThread extends Thread { //класс в котором мы будем реализовывать потоки. Он наследуется от Thread
    protected float[] array; //половина исходного массива которую мы будем обрабатывать в новом потоке

    CalculationThread(float[] array) { //конструктор который принимает на вход массив который мы хотим преобразовать
        this.array = array;
    }

    @Override
    public void run(){ //run - это главный метод в любом классе унаследованном от класса Thread
        float[] a1 = calculation(array);
        System.arraycopy(a1, 0, array, 0, a1.length);
    }

    public float[] calculation(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2F + i / 5) * Math.cos(0.2F + i / 5) * Math.cos(0.4F + i / 2));
        } //пробегаемся по массиву и преобразовываем его элементы по формуле
        return array; //возвращаем массив float - 32 битный дробный тип
    }
}
