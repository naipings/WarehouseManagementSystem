select * from instock
delete from instock where ID=1

UPDATE instock set supname='666公司', stockname='aaa', num=500, price=56.30 where id=3

select * from outstock
delete from outstock

select outstock.ID,outstock.supname,outstock.stockname,outstock.outtime,outstock.num,outstock.price,product.stock,outstock.`user` from outstock, product where product.supname=outstock.supname and product.`name`=outstock.stockname

select outstock.ID,outstock.supname,outstock.stockname,outstock.outtime,outstock.num,outstock.price,product.stock,outstock.`user` from outstock, product where product.supname=outstock.supname and product.`name`=outstock.stockname and id=1


