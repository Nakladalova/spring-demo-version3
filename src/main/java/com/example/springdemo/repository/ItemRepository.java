package com.example.springdemo.repository;

import com.example.springdemo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
@Repository
public interface ItemRepository extends JpaRepository<Item,Integer>, CrudRepository<Item, Integer> {

    @Query(value ="SELECT p.product_name, i.price, i.amount, i.item_id, p.product_id, s.shoppingcart_id FROM public.item i " +
            "INNER JOIN public.product p ON i.product_id= p.product_id " +
            "INNER JOIN public.shoppingcart s ON i.shoppingcart_id = s.shoppingcart_id " +
            "WHERE s.user_id = ?1", nativeQuery = true)
    Collection<Item> findAllItems(int userId);

     /*@Query(value ="SELECT p.product_name, p.price, i.amount, i.item_id, p.product_id, s.shoppingcart_id FROM public.item i " +
            "INNER JOIN public.product p ON i.product_id= p.product_id " +
            "INNER JOIN public.shoppingcart s ON i.shoppingcart_id = s.shoppingcart_id " +
            "WHERE s.user_id = ?1", nativeQuery = true)
    Collection<Item> findAllItems(int user_id);*/

    @Query("DELETE FROM Item i WHERE i.shoppingcartId = ?1")
    @Modifying
    public void deleteItems(long shoppingcartId);

    @Query(value ="SELECT amount FROM public.item WHERE product_id = ?1 AND shoppingcart_id = ?2", nativeQuery = true)
    public Integer findItem(int productId, int shoppingcartId);

    @Query(value ="UPDATE Item i SET amount = ?1 WHERE product_id = ?2 AND shoppingcart_id = ?3", nativeQuery = true)
    @Modifying
    public void updateItem(int updatedAmount, int productId, int shoppingcartId);

    @Query(value = "DELETE FROM Item i WHERE shoppingcart_id = ?1 AND product_id= ?2", nativeQuery = true)
    @Modifying
    public void deleteItem(int shoppingcartId, int productId);
}
