package ru.geekbrains;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Sample02 {

    private static Random random = new Random();

    /**
     * TODO: 2. generateEmployee должен создавать различных сотрудников (Worker, Freelancer)
     * @return
     */
    static Employee generateEmployee(){
        String[] names = new String[] { "Анатолий", "Глеб", "Клим", "Мартин", "Лазарь", "Владлен", "Клим", "Панкратий", "Рубен", "Герман" };
        String[] surnames = new String[] { "Григорьев", "Фокин", "Шестаков", "Хохлов", "Шубин", "Бирюков", "Копылов", "Горбунов", "Лыткин", "Соколов" };
        String[] specialities = new String[]{"программист", "сварщик", "менеджер", "слесарь", "токарь"};

        int salary = random.nextInt(100, 200);
        int index = random.nextInt(100, 200);
        int workingDays = random.nextInt(20);
        if(random.nextInt(1,3) == 1){
            return new Worker(names[random.nextInt(10)], surnames[random.nextInt(10)], salary * index, specialities[random.nextInt(5)]);
        }
        else{
            return new Freelancer(names[random.nextInt(10)], surnames[random.nextInt(10)], salary, specialities[random.nextInt(5)], workingDays);
        }
    }

    public static void main(String[] args) {

        Employee[] employees = new Employee[10];
        for (int i = 0; i < employees.length; i++)
            employees[i] = generateEmployee();

        for (Employee employee : employees){
            System.out.println(employee);
        }

        //Arrays.sort(employees, new NameComparator());
        Arrays.sort(employees, new SpecialityComparator());

        System.out.printf("\n*** Отсортированный массив сотрудников ***\n\n");

        for (Employee employee : employees){
            System.out.println(employee);
        }

    }

}

class SalaryComparator implements Comparator<Employee> {


    @Override
    public int compare(Employee o1, Employee o2) {

        //return o1.calculateSalary() == o2.calculateSalary() ? 0 : (o1.calculateSalary() > o2.calculateSalary() ? 1 : -1);
        return Double.compare( o2.calculateSalary(), o1.calculateSalary());
    }
}

class NameComparator implements Comparator<Employee> {


    @Override
    public int compare(Employee o1, Employee o2) {
        //return o1.calculateSalary() == o2.calculateSalary() ? 0 : (o1.calculateSalary() > o2.calculateSalary() ? 1 : -1);
        int res = o1.name.compareTo(o2.name);
        if (res == 0){
            res = Double.compare( o2.calculateSalary(), o1.calculateSalary());
        }
        return res;
    }
}

class SpecialityComparator implements Comparator<Employee>{
    @Override
    public int compare(Employee o1, Employee o2) {
        int res = o1.speciality.compareTo(o2.speciality);
        if(res == 0){
            res = o1.name.compareTo(o2.name);
        }
        return res;
    }
}

abstract class Employee implements Comparable<Employee>{

    protected String name;
    protected String surName;
    protected double salary;
    protected String speciality;

    public Employee(String name, String surName, double salary, String speciality) {
        this.name = name;
        this.surName = surName;
        this.salary = salary;
        this.speciality = speciality;
    }

    public abstract  double calculateSalary();

    @Override
    public String toString() {
        return String.format("Сотрудник: %s %s; Специальность: %s; Среднемесячная заработная плата: %.2f", name, surName, speciality, salary);
    }

    @Override
    public int compareTo(Employee o) {
        return Double.compare( o.calculateSalary(), calculateSalary());
    }
}

class Worker extends Employee{

    public Worker(String name, String surName, double salary, String speciality) {
        super(name, surName, salary, speciality);
    }

    @Override
    public double calculateSalary() {
        return salary ;
        //TODO: Для фрилансера - return 20 * 5 * salary
    }

    @Override
    public String toString() {        
        String type = "Штатный сотрудник";
        String salaryType = "Среднемесячная заработная плата (фиксированная месячная оплата):";
        // "%-10s %-10s; %-18s;  Специальность: %-12s; %-65s: %-7.2f (руб.); рабочих дней %-4d; сумма за месяц %-8.2f", name, surName, type, speciality, salaryType, salary, workingDays, salary * workingDays * 8
        return String.format("%-10s %-10s; %-18s;  Специальность: %-12s| %-65s:       %-7.2f (руб.)", name, surName, type, speciality, salaryType, salary);
    }
}

/**
 * TODO: 1. Доработать самостоятельно в рамках домашней работы
 */
class Freelancer extends Employee{

    private int workingDays;

    public Freelancer(String name, String surName, double salary, String speciality, int workingDays) {
        super(name, surName, salary, speciality);
        this.workingDays = workingDays;
    }

    @Override
    public double calculateSalary() {
        return salary * workingDays * 8;
    }

    @Override
    public String toString() {
        String type = "Фрилансер";
        String salaryType = "Заработная плата (в час):";
        return String.format("%-10s %-10s; %-18s;  Специальность: %-12s| %-25s %-7.2f(руб.); рабочих дней %-2d; сумма за месяц %-8.2f (руб.)", name, surName, type, speciality, salaryType, salary, workingDays, salary * workingDays * 8);
    }
}