(ns cms.datomic.schema
  (:use zolodeck.demonic.schema)
  (:require [datomic.api :as db]))

(def author-schema
  [(uuid-fact-schema :author/guid false "A author's guid" false)
   (string-fact-schema :author/name false "A author's name" false)
   (string-fact-schema :author/email false "A author's email" false)
   (refs-fact-schema :author/articles false "A author's articles" false)])

(def article-schema
  [(uuid-fact-schema :article/guid false "A article's guid" false)
   (string-fact-schema :article/title true "A article's title" false)
   (string-fact-schema :article/body true "A article's body" false)
   (enum-fact-schema :article/category false "A article's category" false)])

(def category-enum-schema
  [(enum-value-schema :category/tech)
   (enum-value-schema :category/business)
   (enum-value-schema :category/fun)])

(defn all []
  (concat 
   author-schema
   article-schema
   category-enum-schema))