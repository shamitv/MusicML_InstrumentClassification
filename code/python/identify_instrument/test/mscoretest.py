from config import Conf
import subprocess

conf = Conf()

print(conf.MUSESCORE_BIN)



midiName = "d0e099885ff2594c2e2d895dbf57f45a"
midi_file = conf.GENERATED_SONGS + conf.FILE_PATH_SEPARATOR \
            + "FLUTE" + conf.FILE_PATH_SEPARATOR + midiName[0] \
            + conf.FILE_PATH_SEPARATOR + midiName + ".mid"

print(midi_file)

midiOutDir = conf.EXPLORATION_DIR + conf.FILE_PATH_SEPARATOR + "tmp"
midiOutFile = midiOutDir + conf.FILE_PATH_SEPARATOR + midiName + ".wav"

cmd_args = [conf.MUSESCORE_BIN, "-o" , midiOutFile , midi_file]

print(cmd_args)

subprocess.run(cmd_args)