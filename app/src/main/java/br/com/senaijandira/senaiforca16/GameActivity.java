    package br.com.senaijandira.senaiforca16;

    import android.annotation.SuppressLint;
    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.Drawable;
    import android.media.MediaPlayer;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import java.util.ArrayList;
    import java.util.Random;

    /**
     * Created by 16254850 on 13/08/2018.
     */

    public class GameActivity extends Activity {

        class Fases{
            public int acertos;
            public int erros;
            public int nivel=0;
        };

        private LinearLayout PainelResposta;
        private TextView TxtChances;
        private ImageView boneco;
        private Fases fase = new Fases();
        private ArrayList<String> letras =new ArrayList<String>();
        private ArrayList<String> PalavrasSorteadas =new ArrayList<String>();
        //19 letras
        String [] Palavras  = {"batata","cachorro","melhores","vais","meninas","dias","bolas","chamas","uva","ovo","sapateiro","pedreiro","oi","senai","monitor","mause","teclado","computador","mesa"};
        private int Palavra = 0;
        private int chances = 10;
        private int acertos = 0;
        private int faseBoneco = 9;
        private MediaPlayer player;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
            PainelResposta = (LinearLayout) findViewById(R.id.PainelResposta);
            TxtChances = (TextView) findViewById(R.id.TxtChances);
            boneco = findViewById(R.id.boneco);
            faseBoneco = 9;
            SelecionaPalavra();
            Joga();
        }
        public void verifica(View v){
            //int tag = Integer.parseInt(v.getTag().toString());

            Button btnTmp = (Button) v ;

            letras.add(btnTmp.getText().toString());
            btnTmp.setEnabled(false);
            if(chances!=0){
                chances--;
                if (Joga() == 0) {
                    btnTmp.setBackgroundColor(Color.parseColor("#21ff00"));
                    btnTmp.setTextColor(Color.parseColor("#ffffff"));
                    btnTmp.setText("V");
                    fase.acertos++;
                } else if (Joga() == 1) {
                    btnTmp.setBackgroundColor(Color.parseColor("#ff0000"));
                    btnTmp.setTextColor(Color.parseColor("#ffffff"));
                    btnTmp.setText("F");
                    MontaBoneco();
                    faseBoneco--;
                    fase.erros++;
                }
                btnTmp.setHeight(30);
                btnTmp.setWidth(20);
            }else{
                fase.nivel++;
                player = MediaPlayer.create(this,R.raw.noo);
                player.start();
                alert("Você Perdeu!!","Continue na proxima etapa");
                Pular(null);
            }
        }
        private void MontaBoneco(){
            System.out.println("FASE DO BONECO: "+faseBoneco);
            switch(faseBoneco){
                case 9:
                    Drawable drawable= getResources().getDrawable(R.drawable.imagemforca9);
                    boneco.setImageDrawable(drawable);
                    break;
                case 8:
                    Drawable drawable1= getResources().getDrawable(R.drawable.imagemforca8);
                    boneco.setImageDrawable(drawable1);
                    break;
                case 7:
                    Drawable drawable2= getResources().getDrawable(R.drawable.imagemforca7);
                    boneco.setImageDrawable(drawable2);
                    break;
                case 6:
                    Drawable drawable3= getResources().getDrawable(R.drawable.imagemforca6);
                    boneco.setImageDrawable(drawable3);
                    break;
                case 5:
                    Drawable drawable4= getResources().getDrawable(R.drawable.imagemforca5);
                    boneco.setImageDrawable(drawable4);
                    break;
                case 4:
                    Drawable drawable5= getResources().getDrawable(R.drawable.imagemforca4);
                    boneco.setImageDrawable(drawable5);
                    break;
                case 3:
                    Drawable drawable6= getResources().getDrawable(R.drawable.imagemforca3);
                    boneco.setImageDrawable(drawable6);
                    break;
                case 2:
                    Drawable drawable7= getResources().getDrawable(R.drawable.imagemforca3);
                    boneco.setImageDrawable(drawable7);
                    break;
                case 1:
                    Drawable drawable8= getResources().getDrawable(R.drawable.imagemforca2);
                    boneco.setImageDrawable(drawable8);
                    break;
            }
        }
        private boolean achado(String letra){
            for(int i=0;i<letras.size();i++) {
                if(letras.get(i).toString().equals(letra)){
                    System.out.println(" Letra compativel: "+letra+" = "+letras.get(i).toString());
                    return true;
                }
            }
            return false;
        }
        @SuppressLint("ResourceAsColor")
        public int Joga(){
            PainelResposta.removeAllViews(); // Retira todos os itens que existiam no Painel
            TxtChances.setText("Numero de chances:"+chances);
            int letrasEncontradas=0;
            int letrasachadas=0;
            for(int j = 0; j < Palavras[Palavra].length(); j++){
                TextView txttmp = new TextView(this);
                if(achado(Palavras[Palavra].substring(j,j+1).toString())){
                    txttmp.setText(Palavras[Palavra].substring(j,j+1).toString());
                    txttmp.setTextSize(18);
                    letrasEncontradas++;
                    letrasachadas++;
                }else{
                    txttmp.setText("_");
                    txttmp.setTextSize(12);
                    letrasEncontradas--;
                }
                txttmp.setHeight(50);
                txttmp.setWidth(50);
                txttmp.setTextColor(R.color.text);
                PainelResposta.addView(txttmp);
            }
            if(letrasEncontradas==Palavras[Palavra].length()){
                System.out.println("O usuario finalizou a fase!!");
                alert("Parabens"," vc finalizou com "+chances+" restantes!");
                fase.acertos++;
                fase.nivel++;
                SelecionaPalavra();
                letras.clear();
                chances=10;
                acertos=0;
                player = MediaPlayer.create(this,R.raw.yess);
                player.start();
                onCreate(null);
                return 2;
            }
            System.out.println(" Total de letras encontradas : "+letrasEncontradas+" , total de acertos atuais :"+acertos);
            if(acertos<letrasachadas){
                acertos = letrasachadas;
                return 0;
            }else{
                return 1;
            }
        }
        public void alert(String titulo, String msg){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(titulo);
            alert.setMessage(msg);
            alert.create().show();
        }
        public void GameOver(View v){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over!");
            alert.setMessage("Você teve "+fase.acertos+" acertos e "+fase.erros+" erros em "+fase.nivel+" rodadas");
            alert.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(mainActivity);
                }
            });
            alert.create().show();
        }

        private void SelecionaPalavra(){
            Random gerador = new Random();
            while(true) {
                int indice = gerador.nextInt(Palavras.length);//Pega um numero que esteja entre 0 e o tamanho da array palavras
                if (ProcuraPalavraSorteada(indice)) {
                    Palavra=indice;
                    break;
                }
            }
        }
        private boolean ProcuraPalavraSorteada(int palavraSorteada){
            for(int i=0;i<PalavrasSorteadas.size();i++) {
                if(Palavras[palavraSorteada].equals(PalavrasSorteadas.get(i))){
                    return false;
                }
            }
            return true;
        }

        public void Pular(View view) {
            player = MediaPlayer.create(this,R.raw.batman);
            player.start();
            alert("Palando","Passado para a proxima etapa");
            SelecionaPalavra();
            letras.clear();
            chances=10;
            acertos=0;
            onCreate(null);
        }
    }
