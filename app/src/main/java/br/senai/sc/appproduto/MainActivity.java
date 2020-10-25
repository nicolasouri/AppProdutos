package br.senai.sc.appproduto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.appproduto.database.ProdutoDAO;
import br.senai.sc.appproduto.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private ListView listaProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");

        listaProdutos = findViewById(R.id.listView_produtos);
        listaProdutos.setLongClickable(true);

        definirOnClickListenerListView();
        definirOnLongClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this, android.R.layout.simple_list_item_1, produtoDAO.listar());
        listaProdutos.setAdapter(adapterProdutos);
    }

    private void definirOnClickListenerListView(){
        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoClicado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produtoEdicao", produtoClicado);
                startActivity(intent);
            }
        });
    }

    private void definirOnLongClickListenerListView(){
        listaProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = adapterProdutos.getItem(position);
                ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
                produtoDAO.excluir(produto);
                adapterProdutos.remove(produto);
                adapterProdutos.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Produto Deletado", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }


    public void onClickNovoProduto(View v){
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivity(intent);
    }
}
