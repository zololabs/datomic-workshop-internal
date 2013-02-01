(ns cms.datomic.schema
  (:require [datomic.api :as db]))

(def author-schema
  [;; name
   {:db/id #db/id[:db.part/db]
    :db/ident :author/name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A author's name"
    :db.install/_attribute :db.part/db}

   ;; email
   {:db/id #db/id[:db.part/db]
    :db/ident :author/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A author's email"
    :db.install/_attribute :db.part/db}

   ;; articles
   {:db/id #db/id[:db.part/db]
    :db/ident :author/articles
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many
    :db/doc "A author's article"
    :db.install/_attribute :db.part/db}])

(def article-schema
  [;; title
   {:db/id #db/id[:db.part/db]
    :db/ident :article/title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/fulltext true
    :db/doc "A article's title"
    :db.install/_attribute :db.part/db}

   ;; body
   {:db/id #db/id[:db.part/db]
    :db/ident :article/body
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/fulltext true
    :db/doc "A article's body"
    :db.install/_attribute :db.part/db}

   ;; category
   {:db/id #db/id[:db.part/db]
    :db/ident :article/category
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc "A article category enum value"
    :db.install/_attribute :db.part/db}
   
   ])

(def category-enum-schema
  [[:db/add #db/id[:db.part/user] :db/ident :category/tech]
   [:db/add #db/id[:db.part/user] :db/ident :category/business]
   [:db/add #db/id[:db.part/user] :db/ident :category/fun]])


(defn all []
  (concat 
   author-schema
   article-schema
   category-enum-schema))