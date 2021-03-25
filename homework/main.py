from generators import publication_generator, subs_generator

if __name__ == '__main__':
    print("================= START GENERATE PUBLICATION =================")
    publication_generator.start()
    print("================= PUBLICATIONS GENERATED =================")
    print("================= START GENERATE SUBSCRIPTIONS =================")
    subs_generator.start()
    print("================= SUBSCRIPTION GENERATED =================")
    print("================= DONE =================")
