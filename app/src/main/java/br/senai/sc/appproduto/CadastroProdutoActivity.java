package br.senai.sc.appproduto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.senai.sc.appproduto.database.ProdutoDAO;
import br.senai.sc.appproduto.modelo.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produto");

        carregarProduto();
    }

    public void carregarProduto(){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().get("produtoEdicao") != null) {

            Produto produto = (Produto) intent.getExtras().get("produtoEdicao");

            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextValor = findViewById(R.id.editText_valor);
            editTextNome.setText(produto.getNome());
            editTextValor.setText(produto.getValor().toString());
            id = produto.getId();
        }
    }

    public void onClickSalvar(View v){
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextValor = findViewById(R.id.editText_valor);

        String nome = editTextNome.getText().toString();
        float valor = Float.parseFloat(editTextValor.getText().toString());

        Produto produto = new Produto(id, nome, valor);
        Intent intent = new Intent();
        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        boolean salvou = produtoDAO.salvar(produto);
        if (salvou){
            finish();
        }else{
            Toast.makeText(CadastroProdutoActivity.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickVoltar(View v){
        finish();
    }
}
