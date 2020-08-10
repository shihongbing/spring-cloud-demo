package com.saltfishsoft.springclouddemo.baseserver.repository;

import com.saltfishsoft.springclouddemo.common.domain.BusinessObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/29.
 * 由于项目中的 实体（entity）默认都是继承一个父类（包含一些公共的属性，比如创建时间，修改时间，是否删除，主键id）。为了实现逻辑删除，
 * 一般会自己实现RepositoryFactoryBean和 Repository。但是由于多个团队开发的结果，
 * 表的结构没有同一，也就是会出现有的表没有基础父类对应的字段，这样就会导致自定义的jpa repository操作这些表就会出错。
 */
public class CustomRepositoryFactoryBean<R extends JpaRepository<T,ID>,T extends BusinessObject,ID extends Serializable>
        extends JpaRepositoryFactoryBean<R,T,ID> {


    public CustomRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager){
        return new CustomRepositoryFactory(entityManager);
    }

    private static class CustomRepositoryFactory<T extends BusinessObject, ID extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager entityManager;

        public CustomRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

//        @Override
//        protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository
//                (RepositoryInformation information, EntityManager entityManager){
//            return new BaseRepositoryImpl((Class<T>)information.getDomainType(),entityManager);
//        }

        protected Object getTargetRepository(RepositoryMetadata metadata) {
            return new BaseRepositoryImpl<T,ID>(
                    (Class<T>) metadata.getDomainType(), entityManager);
        }
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata){
            return BaseRepositoryImpl.class;
        }
    }

    /**
     * 改进之后,由于有的表没有默认父类BusinessObject对应的字段，导致生成 Repository 在操作表的时候会报错。
     * 需要修改的就是RepositoryFactoryBean的实现逻辑。对于继承了BusinessObject的实体类，
     * 返回自定义的BaseRepository（也就是BaseRepositoryImpl），否则就返回SimpleJpaRepository。
     * 注意自定义CustomRepositoryFactoryBean的泛型也做了修改。
     * @param entityManager
     * @return
     */
//    @Override
//    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
//        return new JpaRepositoryFactory(entityManager) {
//
//            protected SimpleJpaRepository<T, Serializable> getTargetRepository(
//                    RepositoryInformation information,    EntityManager entityManager) {
//                Class<T> domainClass = (Class<T>) information.getDomainType();
//                if(BusinessObject.class.isAssignableFrom(domainClass)) {
//                    return new BaseRepositoryImpl(domainClass, entityManager);
//                } else {
//                    return new SimpleJpaRepository(domainClass, entityManager);
//                }
//            }
//
//            @Override
//            protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//                return metadata.getDomainType().isAssignableFrom(BusinessObject.class) ? BaseRepositoryImpl.class : SimpleJpaRepository.class;
//            }
//        };
//    }

}
