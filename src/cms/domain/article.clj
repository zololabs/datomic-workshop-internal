(ns cms.domain.article
  (:require [datomic.api :as api]
            [cms.datomic.core :as db]
            [cms.domain.tag :as tag]))

(defn create [title body category]
  (let [t (api/tempid :db.part/user)
        a {:article/title title
           :article/body body
           :article/category category
           :db/id t}
        {:keys [db-after tempids]} (->> a
                                        vector
                                        (api/transact db/CONN)
                                        deref)
        ar-id (api/resolve-tempid db-after tempids t)]
    (->> ar-id
         (api/entity db-after)
         api/touch)))

(defn find-all []
  (->> (api/q '[:find ?e :where [?e :article/title]] (api/db db/CONN))
       (map #(api/entity (api/db db/CONN) (first %)))
       (map api/touch)))

(defn add-tag [ar tag-name]
  (let [t (tag/create tag-name)]
    (->> [:db/add (:db/id ar) :article/tags (:db/id t)]
         vector
         (api/transact db/CONN)
         deref)))

(defn update-tags [ar new-tag-names]
  (->> [:db/retract (:db/id ar) :article/tags (map :db/id (:article/tags ar))]
       vector
       (api/transact db/CONN)
       deref)

  (let [tags (doall (map tag/create new-tag-names))]
    (println "Updating tags")
    (->> [:db/add (:db/id ar) :article/tags (map :db/id tags)]
         vector
         (api/transact db/CONN)
         deref)))

(defn siblings [ar]
  (->> ar
       :author/_articles
       first
       :author/articles
       (map api/touch)))

(defn find-by-tags [tag-names]
  (let [db (api/db db/CONN)]
    (->> (api/q '[:find ?a
                  :in $ [?n ...]
                  :where
                  [?a :article/tags ?t]
                  [?t :tag/name ?n]]
                db tag-names)
         (map #(api/entity db (first %)))
         (map api/touch))))

(defn find-by-tags-rules [tag-names]
  (let [db (api/db db/CONN)
        rules '[[[tag-name? ?t ?name]
                 [?t :tag/name ?name]]]]
    (->> (api/q '[:find ?a
                  :in $ [?n ...] %
                  :where
                  [?a :article/tags ?t]
                  (tag-name? ?t ?n)]
                db tag-names rules)
         (map #(api/entity db (first %)))
         (map api/touch))))

(defn category-articles []
  (let [db (api/db db/CONN)
        rules '[[[category? ?a ?c]
                 [?a :article/category ?c]]
                [[fun? ?a]
                 (category? ?a :category/fun)]
                [[tech? ?a]
                 (category? ?a :category/tech)]]]
    (->> (api/q '[:find ?a
                  :in $  %
                  :where
                  [?a :article/title]
                  (tech? ?a)]
                db rules)
         (map #(api/entity db (first %)))
         (map api/touch))))

(defn search [search-string]
  (let [db (api/db db/CONN)]
    (->> (api/q '[:find ?a
                  :in $ ?s [?att ...]
                  :where
                  [(fulltext $ ?att ?s) [[?a]]]]
                db search-string [:article/body :article/title])
         (map #(api/entity db (first %)))
         (map api/touch))))

(defn titles-starting-with [string]
  (let [db (api/db db/CONN)]
    (->> (api/q '[:find ?a
                  :in $ ?string
                  :where
                  [?a :article/title ?t]
                  [(.startsWith ?t ?string)]]
                db string)
         (map #(api/entity db (first %)))
         (map api/touch))))
