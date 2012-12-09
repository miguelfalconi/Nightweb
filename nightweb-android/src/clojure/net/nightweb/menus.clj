(ns net.nightweb.menus
  (:use [neko.resource :only [get-string get-resource]]
        [net.nightweb.actions :only [share-url
                                     show-new-post]]))

(defn create-main-menu
  [context menu]
  (let [search-item (.add menu (get-string :search))
        new-post-item (.add menu (get-string :new_post))
        share-item (.add menu (get-string :share))
        if-room android.view.MenuItem/SHOW_AS_ACTION_IF_ROOM
        collapse-action-view
        android.view.MenuItem/SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
        search-view (android.widget.SearchView. context)]
    (.setIcon search-item (get-resource :drawable :android/ic_menu_search))
    (.setIcon new-post-item (get-resource :drawable :android/ic_menu_add))
    (.setIcon share-item (get-resource :drawable :android/ic_menu_share))
    (.setShowAsAction search-item (bit-or if-room collapse-action-view))
    (.setShowAsAction new-post-item if-room)
    (.setShowAsAction share-item if-room)
    (.setOnQueryTextListener
      search-view
      (proxy [android.widget.SearchView$OnQueryTextListener] []
        (onQueryTextChange [new-text]
          (println "typing:" new-text)
          true)
        (onQueryTextSubmit [query]
          (println "submitted:" query)
          true)))
    (.setActionView search-item search-view)
    (.setOnMenuItemClickListener
      new-post-item
      (proxy [android.view.MenuItem$OnMenuItemClickListener] []
        (onMenuItemClick [menu-item]
          (show-new-post context {})
          true)))
    (.setOnMenuItemClickListener
      share-item
      (proxy [android.view.MenuItem$OnMenuItemClickListener] []
        (onMenuItemClick [menu-item]
          (share-url context)
          true)))))