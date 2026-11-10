import javaSimulation.*;
import javaSimulation.Process;

// trida celeho programu (simulace)
public class Kadernictvi extends Process {
    int pocetKaderniku;
    Head zakulisiSalonu = new Head();
    Head fronta = new Head();
    // Random random = new Random(4);
    Random random = new Random();
    double dobaStravenaVSalonu;
    double dobaStrihani; 
    int pocetZakazniku;
    int maxPocetZakazniku;
    int velikostFronty;
    double pocetPrestavek;
    double dobaStravenaNaPrestavkach;
    long startVypoctu = System.currentTimeMillis();
    double delkaGenerovani = 200;
    double casSimulace;
	
    Kadernictvi(int n) {pocetKaderniku = n;}

    // popis akci (prichod kadernika a generovani zakazniku)
    public void actions() {
        for (int i = 1; i <= pocetKaderniku; i++)
            new Kadernik().into(zakulisiSalonu);
        activate(new ZakazniciGenerator());
        hold(delkaGenerovani + 1000000);	
        report();
    }
	
    // vypis z programu (simulace)
    void report() {
        System.out.println("Pocet kaderniku v salonu: " + pocetKaderniku);
        System.out.println("Pocet ostrihanych zakazniku: " + pocetZakazniku);
        System.out.println("Maximalni delka fronty: " + maxPocetZakazniku);
        java.text.NumberFormat fmt = java.text.NumberFormat.getNumberInstance();
        fmt.setMaximumFractionDigits(2);
        System.out.println("Prumerny cas zakaznika straveny v salonu: " + fmt.format(dobaStravenaVSalonu/pocetZakazniku) + " min");     //cekani + strihani dohromady v minutach
        System.out.println("Prumerna doba strihani: " + fmt.format(dobaStrihani/pocetZakazniku) + " min");                                             // jen strihani, urceno v minutach (obecne)
        System.out.println("Prumerna doba stravena cekanim: " + fmt.format((dobaStravenaVSalonu-dobaStrihani)/pocetZakazniku) + " min");
        System.out.println("Pocet prestavek na jednoho kadernika: " + fmt.format(pocetPrestavek/pocetKaderniku));
        System.out.println("Celkovy cas kaderniku straveny na prestavkach: " + fmt.format(dobaStravenaNaPrestavkach * pocetPrestavek) + " min"); 
        System.out.println("Cas trvani simulace: " + casSimulace + " minut");  
        System.out.println("Cas vypoctu simulace: " + fmt.format((System.currentTimeMillis() - startVypoctu)/1000.0) + " secs\n");
    }
	
    // trida na popis akci, ktere provadi zakaznik
    class Zakaznik extends Process {
        public void actions() {
            double prichodDoSalonu = time();
            into(fronta);
            int delkaFronty = fronta.cardinal();
            velikostFronty = delkaFronty;
            if (maxPocetZakazniku < delkaFronty) 
                maxPocetZakazniku = delkaFronty;
            if (!zakulisiSalonu.empty()) 
                activate((Kadernik) zakulisiSalonu.first());
            passivate();
            pocetZakazniku++;
            dobaStravenaVSalonu += time() - prichodDoSalonu;
            casSimulace = time();
        }		
    }

    // trida na popis akci, ktere provadi kadernik
    class Kadernik extends Process {
        int pocetOstrihani = 0;     // sledovani poctu ostrihanych zakazniku na jednoho kadernika

        public void actions() { 
            while (true) { 
                out();
                while (!fronta.empty()) {
                    Zakaznik naRade = (Zakaznik) fronta.first();
                    naRade.out();
                    int randomNumber = random.nextInt(16);         // Random hodnota od 0 do 15
                    int finalNumber = randomNumber + 10;                 // celkova doba strihani od 10 do 25 minut
                    hold(finalNumber);                                   // Simulace trvani strihani (pozdrzeni simulace na 10 casovych jednotek)
                    dobaStrihani += finalNumber;
                    activate(naRade);
                    pocetOstrihani++;

                    // Kontrola, zda kadernik ostrihal 5 zakazniku a neni prilis velka fronta, pokud ano, dava si 5 minut (casovych jednotek) pauzu
                    if (pocetOstrihani >= 5 && velikostFronty <= 5) {
                        pocetPrestavek++;
                        pocetOstrihani = 0;
                        double zacatekPrestavky = time();
                        int randomNumberPause = random.nextInt(4);         // Random hodnota od 0 do 3
                        int finalNumberPause = randomNumberPause + 3;
                        hold(finalNumberPause);
                        dobaStravenaNaPrestavkach = time() - zacatekPrestavky;
                    }
                }
                wait(zakulisiSalonu);
            }
        }
    }

    // trida na generovani prichazenjicich zakazniku do salonu
    class ZakazniciGenerator extends Process {
        public void actions() {
            while (time() <= delkaGenerovani) {
                activate(new Zakaznik());
                hold(random.negexp(1/11.0));
            }
        }
    }

    // main programu (simulace)
    public static void main(String args[]) {
        activate(new Kadernictvi(1));
        activate(new Kadernictvi(2));
        activate(new Kadernictvi(3));
    }
}
