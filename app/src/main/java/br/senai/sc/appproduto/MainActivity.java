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

import br.senai.sc.appproduto.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVO_PRODUTO = 1, RESULT_CODE_NOVO_PRODUTO = 10;
    private final int REQUEST_CODE_PRODUTO_ATUALIZADO = 2, RESULT_CODE_PRODUTO_ATUALIZADO = 20;

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
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this, android.R.layout.simple_list_item_1, produtos);
        listaProdutos.setAdapter(adapterProdutos);

        definirOnClickListenerListView();
        definirOnLongClickListenerListView();
    }

    private void definirOnClickListenerListView(){
        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoClicado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produtoEdicao", produtoClicado);
                startActivityForResult(intent, REQUEST_CODE_PRODUTO_ATUALIZADO);
            }
        });
    }

    private void definirOnLongClickListenerListView(){
        listaProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = adapterProdutos.getItem(position);
                adapterProdutos.remove(produto);
                adapterProdutos.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Produto Deletado", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }


    public void onClickNovoProduto(View v){
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_PRODUTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_NOVO_PRODUTO && resultCode == RESULT_CODE_NOVO_PRODUTO){
            Produto produto = (Produto) data.getExtras().getSerializable("novoProduto");
            produto.setId(++id);
            this.adapterProdutos.add(produto);
        }else if(requestCode == REQUEST_CODE_PRODUTO_ATUALIZADO && resultCode == RESULT_CODE_PRODUTO_ATUALIZADO){
            Produto produtoEditado = (Produto) data.getExtras().get("produtoEditado");
            Toast.makeText(MainActivity.this, "Produto Editado", Toast.LENGTH_LONG).show();
            for(int i = 0; i < adapterProdutos.getCount(); i++){
                Produto produto = adapterProdutos.getItem(i);
                if(produto.getId() == produtoEditado.getId()){
                    adapterProdutos.remove(produto);
                    adapterProdutos.insert(produtoEditado, i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
